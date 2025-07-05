package com.breaditnow.notification.application.internal;

import com.breaditnow.common.domain.OperatingStatus;

public record BakeryInfo(
        Long bakeryId,
        Long ownerId,
        String name,
        String address,
        String phone,
        String profileImageUrl,
        OperatingStatus operatingStatus,
        boolean deleted
) {}
