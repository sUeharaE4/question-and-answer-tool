package com.qa.common_libs.bean_provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@Slf4j
public class DefaultRestTemplate {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplateBuilder().build();
        template.getInterceptors().add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                    ClientHttpRequestExecution execution) throws IOException {
                log.info(String.format(
                        "Start to call external API. URL: %s, Method: %s, Request: %s",
                        request.getURI().toASCIIString(), request.getMethod(),
                        Arrays.toString(body)));
                return execution.execute(request, body);
            }
        });
        return template;
    }
}
