package com.breaditnow.product.application.port.dto.event;

import com.breaditnow.product.domain.Product;

public record ProductDeletedEvent(
        Long productId
) {
    public static ProductDeletedEvent from(Product product) {
        return new ProductDeletedEvent(product.getId());
    }
}
