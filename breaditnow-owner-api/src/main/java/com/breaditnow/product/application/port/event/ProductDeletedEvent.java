package com.breaditnow.product.application.port.event;

import com.breaditnow.product.domain.model.Product;

public record ProductDeletedEvent(
        Long productId
) {
    public static ProductDeletedEvent from(Product product) {
        return new ProductDeletedEvent(product.getId());
    }
}
