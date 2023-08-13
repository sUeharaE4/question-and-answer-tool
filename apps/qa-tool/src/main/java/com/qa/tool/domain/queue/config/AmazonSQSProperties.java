package com.qa.tool.domain.queue.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AmazonSQSProperties {

    @Value("${cloud.sqs.endpoint}")
    private String queueEndpoint;
    @Value("${cloud.sqs.region.static}")
    private String queueRegion;
    @Value("${app.queue.name}")
    private String queueName;

}
