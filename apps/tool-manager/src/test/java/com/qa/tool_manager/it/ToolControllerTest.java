package com.qa.tool_manager.it;

import com.qa.common_libs.dto.tool.ToolCreateRequest;
import com.qa.common_libs.dto.tool.ToolDTO;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ToolControllerTest {

    @Autowired
    private Flyway flyway;
    @Autowired
    private WebTestClient testClient;

    @LocalServerPort
    private int port;

    private String baseUrl = "/api/v1.0/tools";

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres")).withUsername("it")
                    .withPassword("it").withDatabaseName("it");

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("POSTGRES_JDBC_URL", postgres::getJdbcUrl);
        registry.add("POSTGRES_USER_NAME", postgres::getUsername);
        registry.add("POSTGRES_USER_PASSWORD", postgres::getPassword);

    }

    @BeforeEach
    public void cleanUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testCreateTool() throws Exception {
        ToolCreateRequest req = new ToolCreateRequest("name_001", "desc_001");
        testClient.post().uri(baseUrl).bodyValue(req).exchange().expectStatus().isCreated()
                .expectBody(ToolDTO.class).consumeWith(result -> {
                    ToolDTO res = result.getResponseBody();
                    Assertions.assertNotNull(res);
                    Assertions.assertEquals(1L, res.getId());
                    Assertions.assertEquals("name_001", res.getName());
                    Assertions.assertEquals("desc_001", res.getDescription());
                });
    }

    @Test
    public void testListTools() throws Exception {
        int postToolCount = 3;
        for (int i = 0; i < postToolCount; i++) {
            Long expectId = (long) i + 1;
            String name = String.format("name_03d", i);
            String desc = String.format("desc_03d", i);
            ToolCreateRequest req = new ToolCreateRequest(name, desc);
            testClient.post().uri(baseUrl).bodyValue(req).exchange().expectBody(ToolDTO.class)
                    .consumeWith(result -> {
                        ToolDTO res = result.getResponseBody();
                        Assertions.assertNotNull(res);
                        Assertions.assertEquals(expectId, res.getId());
                        Assertions.assertEquals(name, res.getName());
                        Assertions.assertEquals(desc, res.getDescription());
                    });
        }
        testClient.get().uri(baseUrl).exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.numberOfElements").isEqualTo(postToolCount);
    }

    @Test
    public void testFindToolByIdNotFound() throws Exception {
        testClient.get().uri(baseUrl + "/1").exchange().expectStatus().isBadRequest();
    }

    @Test
    public void testFindToolById() throws Exception {
        testClient.post().uri(baseUrl).bodyValue(new ToolCreateRequest("name", "description"))
                .exchange().expectStatus().is2xxSuccessful();
        testClient.get().uri(baseUrl + "/1").exchange().expectStatus().isOk().expectBody()
                .jsonPath("id").isEqualTo(1L).jsonPath("name").isEqualTo("name");
    }
}
