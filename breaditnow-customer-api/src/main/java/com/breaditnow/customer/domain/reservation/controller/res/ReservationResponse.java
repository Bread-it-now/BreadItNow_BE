package com.breaditnow.customer.domain.reservation.controller.res;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long reservationId,
        String status,
        int totalPrice,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime pickupDeadline
) {
    public static ReservationResponse of(Long reservationId, String status, int totalPrice, LocalDateTime pickupDeadline) {
        return new ReservationResponse(reservationId, status, totalPrice, pickupDeadline);
    }
}
