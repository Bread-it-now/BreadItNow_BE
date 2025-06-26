package com.breaditnow.reservation.adapter.out.http;


import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.reservation.application.dto.internal.ProductInfo;
import com.breaditnow.reservation.domain.port.out.ProductApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductApiAdapter implements ProductApiPort {

    @Qualifier("ownerServiceClient")
    private final WebClient ownerServiceClient;

    @Override
    public Map<Long, ProductInfo> findProductsByIds(List<Long> productIds, Long bakeryId) {
        if (CollectionUtils.isEmpty(productIds)) {
            return Collections.emptyMap();
        }

        var responseType = new ParameterizedTypeReference<ApiSuccessResponse<List<ProductInfo>>>() {};

        return ownerServiceClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/internal/api/v1/bakery/{bakeryId}/product")
                        .queryParam("ids", productIds)
                        .build(bakeryId)
                )
                .retrieve()
                .bodyToMono(responseType)
                .map(ApiSuccessResponse::data)
                .map(list -> list.stream().collect(Collectors.toMap(ProductInfo::productId, Function.identity())))
                .onErrorReturn(Collections.emptyMap())
                .block();
    }
}
