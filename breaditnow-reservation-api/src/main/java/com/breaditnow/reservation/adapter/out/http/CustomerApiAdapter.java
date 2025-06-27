package com.breaditnow.reservation.adapter.out.http;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.domain.port.out.CustomerApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerApiAdapter implements CustomerApiPort {
    private final WebClient customerServiceClient;

    @Override
    public Optional<String> findFcmTokenById(Long customerId) {
        log.info("Requesting FCM token for customerId: {}", customerId);

        var responseType = new ParameterizedTypeReference<ApiSuccessResponse<String>>() {};

        return customerServiceClient.get()
                .uri("/internal/api/v1/customer/{customerId}/fcm-token", customerId)
                .retrieve()
                .bodyToMono(responseType)
                .map(ApiSuccessResponse::data)
                .doOnError(error -> log.error("Customer API 호출 실패. customerId: {}, error: {}", customerId, error.getMessage()))
                .onErrorResume(e -> Mono.empty())
                .blockOptional();
    }
}
