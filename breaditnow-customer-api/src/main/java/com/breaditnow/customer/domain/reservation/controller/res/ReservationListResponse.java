package com.breaditnow.customer.domain.reservation.controller.res;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ReservationListResponse(
        Long reservationId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime reservationDate,
        String reservationNumber,
        String status,
        Long bakeryId,
        String bakeryName,
        int totalPrice,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime pickupDeadline
) {
    public static ReservationListResponse of(Reservation reservation) {
        return new ReservationListResponse(
                reservation.getId(),
                reservation.getCreatedAt(),
                reservation.getReservationNumber(),
                reservation.getStatus().name(),
                reservation.getBakery().getId(),
                reservation.getBakery().getName(),
                reservation.getTotalPrice(),
                reservation.getPickupDeadline()
        );
    }
}
