package com.breaditnow.reservation.application.dto.event;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.domain.ProductStatus;

public record ProductUpdatedEvent(
        Long productId,
        String name,
        String imageUrl,
        Money price,
        ProductStatus status
) {}
