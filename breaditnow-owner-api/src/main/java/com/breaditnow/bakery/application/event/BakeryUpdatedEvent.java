package com.breaditnow.bakery.application.event;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.model.OperatingStatus;

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
