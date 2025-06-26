package com.breaditnow.bakery.application.dto.response;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.domain.OperatingStatus;

public record BakeryInfoResponse(
        Long bakeryId,
        String name,
        OperatingStatus operatingStatus,
        boolean isDeleted
) {
    public static BakeryInfoResponse from(Bakery bakery) {
        return new BakeryInfoResponse(
                bakery.getBakeryId(),
                bakery.getName(),
                bakery.getOperatingStatus(),
                bakery.isDeleted()
        );
    }
}
