package com.breaditnow.product.application.dto.response;

import com.breaditnow.common.domain.ProductStatus;
import com.breaditnow.product.domain.model.Product;

public record ProductInfoResponse(
        Long productId,
        Long bakeryId,
        String name,
        String imageUrl,
        Integer price,
        int stock,
        ProductStatus status,
        boolean isDeleted
) {
    public static ProductInfoResponse from(Product product) {
        return new ProductInfoResponse(
                product.getId(),
                product.getBakeryId(),
                product.getProductInfo().name(),
                product.getProductInfo().getProfileImageUrl(),
                product.getSalesPolicy().price().getAmount(),
                product.getSalesPolicy().stock(),
                product.getSalesPolicy().status(),
                product.isDeleted()
        );
    }
}
