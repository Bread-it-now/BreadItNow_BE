package com.breaditnow.product.application.dto.request;

import java.util.List;

public record ProductDisplayOrderUpdateRequest(
        List<ProductOrder> products
) {
    public record ProductOrder(
            Long productId,
            Integer displayOrder
    ) {}
}
