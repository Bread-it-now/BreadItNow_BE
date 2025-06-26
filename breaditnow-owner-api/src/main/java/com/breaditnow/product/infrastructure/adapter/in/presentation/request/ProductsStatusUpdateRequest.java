package com.breaditnow.product.infrastructure.adapter.in.presentation.request;

import com.breaditnow.product.domain.ProductStatus;

import java.util.List;

public record ProductsStatusUpdateRequest(
        List<Long> productIds,
        ProductStatus status
) {}
