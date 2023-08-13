package com.qa.tool.it;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.qa.common_libs.dto.qa.QACreateRequest;
import com.qa.common_libs.dto.qa.QADTO;
import com.qa.common_libs.dto.tool.ToolDTO;
import com.qa.tool.domain.queue.config.AmazonSQSProperties;
import com.qa.tool.domain.tool.model.ToolEndpointConfig;
import com.qa.tool.it.EnableTestContainerContextCustomizerFactory.EnabledTestContainer;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@EnabledTestContainer
public class ToolQAControllerTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private WebTestClient testClient;
    @SpyBean
    private RestTemplate restTemplate;
    @Autowired
    private ToolEndpointConfig toolEndpointConfig;
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    @Autowired
    AmazonSQSProperties sqsProperties;

    @LocalServerPort
    private int port;

    private String baseUrlFmt = "/api/v1.0/qa/tools/%d";

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void cleanUp() {
        flyway.clean();
        flyway.migrate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        setupTestQueue();
    }


    @Test
    public void testCreate() throws Exception {
        Long toolId = 1L;
        String url = String.format(baseUrlFmt, toolId);
        mockFindByToolId(toolId, HttpStatus.OK);
        QACreateRequest req = new QACreateRequest("question", "answer");
        testClient.post().uri(url).bodyValue(req).exchange().expectStatus().isCreated()
                .expectBody(QADTO.class).consumeWith(result -> {
                    QADTO res = result.getResponseBody();
                    Assertions.assertNotNull(res);
                    Assertions.assertEquals(1L, res.getId());
                    Assertions.assertEquals(toolId, res.getToolId());
                    Assertions.assertEquals(req.getQuestion(), res.getQuestion());
                    Assertions.assertEquals(req.getAnswer(), res.getAnswer());
                });
        // for now, tool_id = 1 is exist in DB, so the external API should not be called.
        testClient.post().uri(url).bodyValue(req).exchange().expectStatus().isCreated();
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), eq(null),
                eq(ToolDTO.class));
    }

    @Test
    public void testFailedToCreateNoToolId() throws Exception {
        Long toolId = 1L;
        String url = String.format(baseUrlFmt, toolId);
        mockFindByToolId(toolId, HttpStatus.BAD_REQUEST);
        QACreateRequest req = new QACreateRequest("question", "answer");
        testClient.post().uri(url).bodyValue(req).exchange().expectStatus().isBadRequest();
    }

    @Test
    public void testBulkCreateQA() throws Exception {
        int qaCount = 3;
        Long toolId = 1L;
        String url = String.format(baseUrlFmt + "/bulk", toolId);
        mockFindByToolId(toolId, HttpStatus.OK);
        List<QACreateRequest> req = new ArrayList<>();
        for (int i = 0; i < qaCount; i++) {
            QACreateRequest tmpReq = new QACreateRequest(String.format("question_%05d", i + 1),
                    String.format("answer_%05d", i));
            req.add(tmpReq);
        }
        testClient.post().uri(url).bodyValue(req).exchange().expectStatus().isCreated()
                .expectBodyList(QADTO.class).consumeWith(result -> {
                    List<QADTO> res = result.getResponseBody();
                    Assertions.assertNotNull(res);
                    Assertions.assertEquals(qaCount, res.size());
                    Assertions.assertEquals(toolId, res.get(0).getToolId());
                    Assertions.assertArrayEquals(
                            req.stream().map(QACreateRequest::getQuestion).toArray(),
                            res.stream().map(QADTO::getQuestion).toArray());
                });
    }

    @Test
    public void testSearchToolQA() throws Exception {
        Long toolId = 1L;
        int qaCount = 3;
        bulkCreateQAUtil(toolId, qaCount);
        List<Long> targetIds = List.of(1L, 2L, 4L);
        List<Long> expectIds = List.of(1L, 2L);
        String urlQuery =
                "?ids=" + String.join(",", targetIds.stream().map(String::valueOf).toList());
        testClient.get().uri(String.format(baseUrlFmt + "/search", toolId) + urlQuery).exchange()
                .expectStatus().isOk().expectBody().jsonPath("$.numberOfElements")
                .isEqualTo(expectIds.size()).jsonPath("$.content[0].tool_id").isEqualTo(toolId)
                .jsonPath("$.numberOfElements").isEqualTo(expectIds.size());
    }

    @Test
    public void testSearchNotFound() throws Exception {
        Long toolId = 1L;
        int qaCount = 3;
        mockFindByToolId(toolId, HttpStatus.OK);
        mockFindByToolId(toolId + 1, HttpStatus.BAD_REQUEST);
        bulkCreateQAUtil(toolId, qaCount);
        String url = String.format(baseUrlFmt, toolId + 1);
        testClient.get().uri(url).exchange().expectStatus().isBadRequest();
    }

    @Test
    public void testSearch() throws Exception {
        Long toolId = 1L;
        int qaCount = 3;
        bulkCreateQAUtil(toolId, qaCount);
        String url = String.format(baseUrlFmt, toolId);
        testClient.get().uri(url).exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.numberOfElements").isEqualTo(qaCount);
    }

    private void mockFindByToolId(Long toolId, HttpStatus status) {
        mockServer
                .expect(MockRestRequestMatchers
                        .requestTo(toolEndpointConfig.toFindByToolIdURL(toolId)))
                .andRespond(MockRestResponseCreators.withRawStatus(status.value()));
    }

    private List<QACreateRequest> bulkCreateQAUtil(Long toolId, int qaCount) {
        String url = String.format(baseUrlFmt + "/bulk", toolId);
        mockFindByToolId(toolId, HttpStatus.OK);
        List<QACreateRequest> req = new ArrayList<>();
        for (int i = 0; i < qaCount; i++) {
            QACreateRequest tmpReq = new QACreateRequest(String.format("question_%05d", i + 1),
                    String.format("answer_%05d", i));
            req.add(tmpReq);
        }
        testClient.post().uri(url).bodyValue(req).exchange().expectStatus().isCreated();
        return req;
    }

    private void setupTestQueue() {
        try {
            String queueUrl =
                    amazonSQSAsync.getQueueUrl(sqsProperties.getQueueName()).getQueueUrl();
            amazonSQSAsync.deleteQueue(queueUrl);
        } catch (Exception e) {
            // if test queue does not exits, just create as usual.
        }
        CreateQueueRequest createReq =
                new CreateQueueRequest().withQueueName(sqsProperties.getQueueName())
                        .addAttributesEntry("FifoQueue", "true");
        amazonSQSAsync.createQueue(createReq);
    }
}
