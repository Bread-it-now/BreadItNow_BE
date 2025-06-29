package com.breaditnow.bakery.application.dto.response;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.domain.OperatingStatus;

public record BakeryInfoResponse(
        Long bakeryId,
        Long ownerId,
        String name,
        String address,
        String phone,
        String profileImageUrl,
        OperatingStatus operatingStatus,
        boolean isDeleted
) {
    public static BakeryInfoResponse from(Bakery bakery) {
        return new BakeryInfoResponse(
                bakery.getBakeryId(),
                bakery.getOwnerId(),
                bakery.getName(),
                bakery.getAddress().fullAddress(),
                bakery.getPhoneNumber().value(),
                bakery.getProfileImage().imageUrl(),
                bakery.getOperatingStatus(),
                bakery.isDeleted()
        );
    }
}
