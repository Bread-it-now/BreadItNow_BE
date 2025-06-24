package com.breaditnow.owner.bakery.application.dto.event;

import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.OperatingStatus;

public record BakeryCreatedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
    public static BakeryCreatedEvent from(Bakery bakery) {
        return new BakeryCreatedEvent(bakery.getBakeryId(), bakery.getOperatingStatus());
    }
}
