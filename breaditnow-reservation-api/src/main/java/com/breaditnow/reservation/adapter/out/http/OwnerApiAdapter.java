package com.breaditnow.reservation.adapter.out.http;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Slf4j
@Component
@RequiredArgsConstructor
public class OwnerApiAdapter implements OwnerApiPort {
    private final WebClient ownerServiceClient;

    @Override
    public Optional<BakeryInfo> findBakeryById(Long bakeryId) {
        return ownerServiceClient.get()
                .uri("/internal/api/v1/bakery/{bakeryId}", bakeryId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiSuccessResponse<BakeryInfo>>() {})
                .map(ApiSuccessResponse::data)
                .blockOptional();
    }

    @Override
    public List<ProductInfo> findProductsByIds(List<Long> productIds, Long bakeryId) {
        return ownerServiceClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/internal/api/v1/bakery/{bakeryId}/product")
                        .queryParam("productIds", productIds.toArray())
                        .build(bakeryId)
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiSuccessResponse<List<ProductInfo>>>() {})
                .map(ApiSuccessResponse::data)
                .onErrorResume(e -> Mono.just(emptyList()))
                .block();
    }

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
}
