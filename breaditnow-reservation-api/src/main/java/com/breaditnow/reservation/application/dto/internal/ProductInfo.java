package com.breaditnow.reservation.application.dto.internal;

import com.breaditnow.common.domain.ProductStatus;

public record ProductInfo(
        Long productId,
        Long bakeryId,
        String name,
        String imageUrl,
        Integer price,
        int stock,
        ProductStatus status,
        boolean deleted
) {}
