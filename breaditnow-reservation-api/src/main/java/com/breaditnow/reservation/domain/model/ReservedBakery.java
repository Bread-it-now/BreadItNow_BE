package com.breaditnow.reservation.domain.model;

public record ReservedBakery(
        Long bakeryId,
        String name,
        String address,
        String phone,
        String profileImageUrl
) {
}
