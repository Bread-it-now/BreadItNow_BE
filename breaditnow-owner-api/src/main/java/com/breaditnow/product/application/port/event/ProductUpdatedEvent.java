package com.breaditnow.product.application.port.event;

import com.breaditnow.common.domain.Money;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.model.ProductStatus;

public record ProductUpdatedEvent(
        Long productId,
        String name,
        String imageUrl,
        Money price,
        ProductStatus status
) {
    public static ProductUpdatedEvent from(Product product) {
        return new ProductUpdatedEvent(
                product.getId(),
                product.getProductInfo().name(),
                product.getProductInfo().getProfileImageUrl(),
                product.getSalesPolicy().price(),
                product.getSalesPolicy().status()
        );
    }
}
