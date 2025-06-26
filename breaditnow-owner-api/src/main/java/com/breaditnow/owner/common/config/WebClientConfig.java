package com.breaditnow.owner.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${api.internal.customer-service.url}")
    private String customerServiceUrl;

    @Bean
    public WebClient customerApiClient() {
        return WebClient.builder()
                .baseUrl(customerServiceUrl)
                .build();
    }
}
