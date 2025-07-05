package com.breaditnow.notification.adapter.out.http;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.notification.domain.port.out.CustomerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerApiAdapter implements CustomerApiPort {
    private final WebClient customerServiceClient;

    @Override
    public Optional<String> findFcmTokenById(Long customerId) {
        return customerServiceClient.get()
                .uri("/internal/api/v1/customer/{customerId}/fcm-token", customerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiSuccessResponse<String>>() {})
                .map(ApiSuccessResponse::data)
                .onErrorResume(e -> Mono.empty())
                .blockOptional();
    }
}
