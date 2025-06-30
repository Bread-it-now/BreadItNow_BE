package com.breaditnow.reservation.domain.model;

import com.breaditnow.reservation.application.dto.internal.BakeryInfo;

public record ReservedBakery(
        Long bakeryId,
        String name,
        String address,
        String phone,
        String profileImageUrl
) {
    public static ReservedBakery create(BakeryInfo bakeryInfo){
        return new ReservedBakery(
                bakeryInfo.bakeryId(),
                bakeryInfo.name(),
                bakeryInfo.address(),
                bakeryInfo.phone(),
                bakeryInfo.profileImageUrl()
        );
    }
}
