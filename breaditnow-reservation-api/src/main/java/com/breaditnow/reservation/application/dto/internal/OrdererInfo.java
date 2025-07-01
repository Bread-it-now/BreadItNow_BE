package com.breaditnow.reservation.application.dto.internal;

public record OrdererInfo(
        Long customerId,
        String nickname,
        String phoneNumber
) {
}
