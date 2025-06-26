package com.breaditnow.product.adapter.in.dto.request;

import com.breaditnow.product.domain.model.ProductStatus;

import java.util.List;

public record ProductsStatusUpdateRequest(
        List<Long> productIds,
        ProductStatus status
) {}
