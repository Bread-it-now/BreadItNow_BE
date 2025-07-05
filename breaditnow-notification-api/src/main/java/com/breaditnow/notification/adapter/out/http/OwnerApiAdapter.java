package com.breaditnow.notification.adapter.out.http;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.notification.application.internal.BakeryInfo;
import com.breaditnow.notification.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OwnerApiAdapter implements OwnerApiPort {
    private final WebClient ownerServiceClient;

    @Override
    public Optional<String> findFcmTokenById(Long ownerId) {
        var responseType = new ParameterizedTypeReference<ApiSuccessResponse<String>>() {};

        return ownerServiceClient.get()
                .uri("/internal/api/v1/owner/{ownerId}/fcm-token", ownerId)
                .retrieve()
                .bodyToMono(responseType)
                .map(ApiSuccessResponse::data)
                .onErrorResume(e -> Mono.empty())
                .blockOptional();
    }

    @Override
    public Optional<BakeryInfo> findBakeryInfoById(Long bakeryId) {
        return ownerServiceClient.get()
                .uri("/internal/api/v1/bakery/{bakeryId}", bakeryId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiSuccessResponse<BakeryInfo>>() {})
                .map(ApiSuccessResponse::data)
                .blockOptional();
    }
}
