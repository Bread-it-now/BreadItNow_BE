package com.breaditnow.reservation.application.dto.internal;

import com.breaditnow.common.domain.OperatingStatus;

public record BakeryInfo(
        Long bakeryId,
        Long ownerId,
        String name,
        OperatingStatus operatingStatus,
        boolean deleted
) {}
