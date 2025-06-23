package com.breaditnow.reservation.application.dto.event;

import java.util.List;

public record BakeryInfoUpdatedEvent(
        Long bakeryId,
        String name,
        String fullAddress,
        String profileImageUrl,
        List<String> additionalImageUrls,
        OperatingStatus operatingStatus
) {}