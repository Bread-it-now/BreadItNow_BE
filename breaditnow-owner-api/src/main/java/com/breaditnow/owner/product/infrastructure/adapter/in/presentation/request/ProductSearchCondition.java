package com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request;

import com.breaditnow.owner.product.domain.ProductStatus;
import com.breaditnow.owner.product.domain.ProductType;

public record ProductSearchCondition(
        ProductStatus status,
        ProductType type
) {
    public ProductSearchCondition(String status, String type) {
        this(ProductStatus.from(status), ProductType.from(type));
    }
}
