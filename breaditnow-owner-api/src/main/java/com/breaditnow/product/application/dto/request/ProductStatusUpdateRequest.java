package com.breaditnow.product.application.dto.request;

import com.breaditnow.common.domain.ProductStatus;

public record ProductStatusUpdateRequest(
        ProductStatus status
) {}
