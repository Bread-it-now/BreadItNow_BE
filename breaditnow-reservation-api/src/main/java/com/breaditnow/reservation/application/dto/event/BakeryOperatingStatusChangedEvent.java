package com.breaditnow.reservation.application.dto.event;

public record BakeryOperatingStatusChangedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
}
