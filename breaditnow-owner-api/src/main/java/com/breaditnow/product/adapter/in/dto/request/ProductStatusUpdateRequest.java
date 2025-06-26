package com.breaditnow.product.adapter.in.dto.request;

import com.breaditnow.product.domain.model.ProductStatus;

public record ProductStatusUpdateRequest(
        ProductStatus status
) {}
