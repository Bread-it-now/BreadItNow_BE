package com.breaditnow.product.adapter.in.dto.request;

import com.breaditnow.product.domain.model.ProductStatus;
import com.breaditnow.product.domain.model.ProductType;

public record ProductSearchCondition(
        ProductStatus status,
        ProductType type
) {
    public ProductSearchCondition(String status, String type) {
        this(ProductStatus.from(status), ProductType.from(type));
    }
}
