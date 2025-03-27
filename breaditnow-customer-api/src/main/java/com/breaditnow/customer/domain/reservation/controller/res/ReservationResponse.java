package com.breaditnow.customer.domain.reservation.controller.res;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReservationResponse(
        Long reservationId,
        Integer reservationNumber,
        String bakeryName,
        String status,
        Integer totalReservationProducts,
        int totalPrice,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime reservationDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime pickupDeadline,
        List<ReservationItemResponse> reservationItems
) {
    public static ReservationResponse of(Reservation reservation, List<ReservationProduct> products) {
        int totalCount = products.stream().mapToInt(ReservationProduct::getQuantity).sum();

        return new ReservationResponse(
                reservation.getId(),
                reservation.getReservationNumber(),
                reservation.getBakery().getName(),
                reservation.getStatus().name(),
                totalCount,
                reservation.getTotalPrice(),
                reservation.getCreatedAt(),
                reservation.getPickupDeadline(),
                products.stream()
                        .map(ReservationItemResponse::of)
                        .collect(Collectors.toList())
        );
    }
}
