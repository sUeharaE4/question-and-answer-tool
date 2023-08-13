package com.qa.tool.domain.queue.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class AmazonSQSConfig {

    private final AwsConfig awsConfig;
    private final AmazonSQSProperties sqsProperties;

    @Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(sqsProperties.getQueueEndpoint(),
                sqsProperties.getQueueRegion());
    }

    @Bean
    public AWSCredentials awsCredentials() {
        // TODO: It is better to be able to choose whether to get it from the instance profile or
        // from an environment variable.
        return new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey());
    }

    @Bean
    public AmazonSQSAsync amazonSQSAsync(
            @Autowired AwsClientBuilder.EndpointConfiguration endpointConfiguration,
            @Autowired AWSCredentials credentials) {
        AmazonSQSAsyncClientBuilder builder = AmazonSQSAsyncClientBuilder.standard();
        builder.setEndpointConfiguration(endpointConfiguration);
        builder.setCredentials(new AWSStaticCredentialsProvider(credentials));
        return builder.build();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(@Autowired AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

}
