package com.breaditnow.reservation.application.dto.internal;

import com.breaditnow.common.domain.OperatingStatus;

public record BakeryInfo(
        Long bakeryId,
        String name,
        OperatingStatus operatingStatus,
        boolean deleted
) {}
