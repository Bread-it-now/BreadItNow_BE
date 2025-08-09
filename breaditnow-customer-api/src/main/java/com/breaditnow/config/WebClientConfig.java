package com.breaditnow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${api.internal.reservation-service.url}")
    private String reservationServiceUrl;

    @Value("${api.internal.owner-service.url}")
    private String ownerServiceUrl;

    @Bean(name = "reservationServiceClient")
    public WebClient reservationServiceClient() {
        return WebClient.builder()
                .baseUrl(reservationServiceUrl)
                .build();
    }

    @Bean(name = "ownerServiceClient")
    public WebClient ownerServiceClient() {
        return WebClient.builder()
                .baseUrl(ownerServiceUrl)
                .build();
    }
}
