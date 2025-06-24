package com.breaditnow.reservation.application.dto.event;

public record BakeryUpdatedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
}
