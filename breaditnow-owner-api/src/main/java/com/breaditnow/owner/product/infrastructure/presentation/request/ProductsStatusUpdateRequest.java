package com.breaditnow.owner.product.infrastructure.presentation.request;

import com.breaditnow.owner.product.domain.ProductStatus;

import java.util.List;

public record ProductsStatusUpdateRequest(
        List<Long> productIds,
        ProductStatus status
) {}
