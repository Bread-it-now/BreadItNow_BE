package com.breaditnow.owner.product.infrastructure.presentation.request;

import com.breaditnow.owner.product.domain.ProductStatus;

public record ProductStatusUpdateRequest(
        ProductStatus status
) {}
