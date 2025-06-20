package com.breaditnow.owner.product.infrastructure.presentation.request;

import java.util.List;

public record ProductDisplayOrderUpdateRequest(
        List<ProductOrder> products
) {
    public record ProductOrder(
            Long productId,
            Integer displayOrder
    ) {}
}
