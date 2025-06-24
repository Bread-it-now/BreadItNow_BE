package com.breaditnow.owner.product.infrastructure.adapter.in.presentation.request;

import com.breaditnow.owner.product.domain.ProductStatus;

public record ProductStatusUpdateRequest(
        ProductStatus status
) {}
