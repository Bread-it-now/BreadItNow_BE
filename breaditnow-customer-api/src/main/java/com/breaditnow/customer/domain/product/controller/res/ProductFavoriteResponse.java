package com.breaditnow.customer.domain.product.controller.res;

import com.breaditnow.domain.domain.product.entity.Product;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ProductFavoriteResponse(
        Long productId,
        Long bakeryId,
        String bakeryName,
        String productName,
        String image,
        int price,
        List<String> releaseTimes,
        boolean isBakeryActive,
        boolean isProductActive
) {
    public static ProductFavoriteResponse of(Product product) {
        return ProductFavoriteResponse.builder()
                .productId(product.getId())
                .bakeryId(product.getBakery().getId())
                .bakeryName(product.getBakery().getName())
                .productName(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .releaseTimes(Arrays.stream(product.getReleaseTime().split(";"))
                        .map(String::trim)
                        .collect(Collectors.toList())
                )
                .isBakeryActive(product.getBakery().isActive())
                .isProductActive(product.isActive())
                .build();
    }
}
