package com.breaditnow.owner.product.application.port.dto.event;

import com.breaditnow.owner.product.domain.Product;

public record ProductDeletedEvent(
        Long productId
) {
    public static ProductDeletedEvent from(Product product) {
        return new ProductDeletedEvent(product.getId());
    }
}
