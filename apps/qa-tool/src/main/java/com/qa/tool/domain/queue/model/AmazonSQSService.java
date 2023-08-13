package com.qa.tool.domain.queue.model;

import com.qa.tool.domain.queue.model.vo.UpsertIdList;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AmazonSQSService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    public void sendMessage(String queueName, List<Long> upsertIdList) {
        String message = new UpsertIdList(upsertIdList).toSQSMessage();
        Map<String, Object> messageHeader = Map.of(SqsMessageHeaders.SQS_GROUP_ID_HEADER,
                "upsert_id", SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, "upsert_id");
        queueMessagingTemplate.send(queueName,
                MessageBuilder.withPayload(message).copyHeaders(messageHeader).build());
    }
}
