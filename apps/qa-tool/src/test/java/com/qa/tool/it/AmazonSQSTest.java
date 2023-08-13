package com.qa.tool.it;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.qa.tool.domain.queue.config.AmazonSQSProperties;
import com.qa.tool.domain.queue.model.AmazonSQSService;
import com.qa.tool.it.EnableTestContainerContextCustomizerFactory.EnabledTestContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

// @SpringBootTest(webEnvironment = WebEnvironment.NONE)
@SpringBootTest
@EnabledTestContainer
public class AmazonSQSTest {

    @Autowired
    AmazonSQSProperties sqsProperties;
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    @Autowired
    AmazonSQSService sqsService;


    @BeforeEach
    void setupTestQueue() throws Exception {

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

    @Test
    void testSend() throws InterruptedException {
        sqsService.sendMessage(sqsProperties.getQueueName(), List.of(1L, 2L, 4L));

        String queueUrl = amazonSQSAsync.getQueueUrl(sqsProperties.getQueueName()).getQueueUrl();
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
                .withMaxNumberOfMessages(1).withWaitTimeSeconds(3);
        List<Message> messages = amazonSQSAsync.receiveMessage(receiveMessageRequest).getMessages();
        Assertions.assertEquals("1,2,4", messages.get(0).getBody());
    }
}
