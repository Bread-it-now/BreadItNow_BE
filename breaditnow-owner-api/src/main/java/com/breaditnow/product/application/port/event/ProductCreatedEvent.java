package com.breaditnow.product.application.port.event;

import com.breaditnow.common.domain.Money;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.model.ProductStatus;

public record ProductCreatedEvent(
        Long productId,
        Long bakeryId,
        String name,
        String imageUrl,
        Money price,
        ProductStatus status
) {
    public static ProductCreatedEvent from(Product product) {
        return new ProductCreatedEvent(
                product.getId(),
                product.getBakeryId(),
                product.getProductInfo().name(),
                product.getProductInfo().getProfileImageUrl(),
                product.getSalesPolicy().price(),
                product.getSalesPolicy().status()
        );
    }
}
