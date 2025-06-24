package com.breaditnow.owner.bakery.application.dto.event;

import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.OperatingStatus;

public record BakeryUpdatedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
    public static BakeryUpdatedEvent from(Bakery bakery) {
        return new BakeryUpdatedEvent(
                bakery.getBakeryId(),
                bakery.getOperatingStatus()
        );
    }
}
