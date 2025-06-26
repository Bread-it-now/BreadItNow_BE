package com.breaditnow.product.infrastructure.adapter.in.presentation.request;

import com.breaditnow.product.domain.ProductStatus;

public record ProductStatusUpdateRequest(
        ProductStatus status
) {}
