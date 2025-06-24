package com.breaditnow.owner.bakery.application.dto.event;

import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.OperatingStatus;

public record BakeryOperatingStatusChangedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
    public static BakeryOperatingStatusChangedEvent from(Bakery bakery) {
        return new BakeryOperatingStatusChangedEvent(
                bakery.getBakeryId(),
                bakery.getOperatingStatus()
        );
    }
}
