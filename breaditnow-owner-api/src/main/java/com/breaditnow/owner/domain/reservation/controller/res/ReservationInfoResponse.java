package com.breaditnow.owner.domain.reservation.controller.res;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationInfoResponse(
        Long reservationId,
        int reservationNumber,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime reservationDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime pickupDeadline,
        String status,
        int totalPrice,
        List<ReservationItemResponse> reservationItems
) {
    public static ReservationInfoResponse of(Reservation reservation, List<ReservationProduct> products) {
        return new ReservationInfoResponse(
                reservation.getId(),
                reservation.getReservationNumber(),
                reservation.getCreatedAt(),
                reservation.getPickupDeadline(),
                reservation.getStatus().name(),
                reservation.getTotalPrice(),
                products.stream().map(ReservationItemResponse::of).toList()
        );
    }
}
