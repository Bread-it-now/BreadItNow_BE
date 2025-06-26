package com.breaditnow.product.infrastructure.adapter.in.presentation.request;

import java.util.List;

public record ProductDisplayOrderUpdateRequest(
        List<ProductOrder> products
) {
    public record ProductOrder(
            Long productId,
            Integer displayOrder
    ) {}
}
