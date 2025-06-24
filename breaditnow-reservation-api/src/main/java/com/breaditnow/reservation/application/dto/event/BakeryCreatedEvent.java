package com.breaditnow.reservation.application.dto.event;

public record BakeryCreatedEvent(
        Long bakeryId,
        OperatingStatus operatingStatus
) {
}

