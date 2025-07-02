package com.breaditnow.product.application.dto.request;

import com.breaditnow.common.domain.ProductStatus;

import java.util.List;

public record ProductsStatusUpdateRequest(
        List<Long> productIds,
        ProductStatus status
) {}
