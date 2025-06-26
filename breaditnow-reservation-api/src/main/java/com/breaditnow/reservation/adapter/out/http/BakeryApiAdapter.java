package com.breaditnow.reservation.adapter.out.http;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.domain.port.out.BakeryApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BakeryApiAdapter implements BakeryApiPort {
    @Qualifier("ownerServiceClient")
    private final WebClient ownerServiceClient;

    @Override
    public Optional<BakeryInfo> findBakeryById(Long bakeryId) {
        var responseType = new ParameterizedTypeReference<ApiSuccessResponse<BakeryInfo>>() {};

        return ownerServiceClient.get()
                .uri("/internal/api/v1/bakery/{id}", bakeryId)
                .retrieve()
                .bodyToMono(responseType)
                .map(ApiSuccessResponse::data)
                .blockOptional();
    }
}
