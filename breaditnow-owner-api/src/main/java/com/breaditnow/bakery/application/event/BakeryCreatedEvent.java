package com.breaditnow.bakery.application.event;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.model.OperatingStatus;

public record BakeryCreatedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
    public static BakeryCreatedEvent from(Bakery bakery) {
        return new BakeryCreatedEvent(bakery.getBakeryId(), bakery.getOperatingStatus());
    }
}
