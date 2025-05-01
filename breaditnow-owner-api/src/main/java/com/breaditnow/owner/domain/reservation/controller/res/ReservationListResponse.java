package com.breaditnow.owner.domain.reservation.controller.res;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationListResponse(
        Long reservationId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime reservationDate,
        int reservationNumber,
        String status,
        Long bakeryId,
        String bakeryName,
        int totalPrice,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime pickupDeadline,
        Integer totalReservationProducts,
        String mainReservationProductName
) {
    public static ReservationListResponse of(Reservation reservation) {
        List<ReservationProduct> products = reservation.getReservationProducts();
        int totalCount = products.stream().mapToInt(ReservationProduct::getQuantity).sum();
        String mainName = products.isEmpty() ? null : products.get(0).getProductName();

        return new ReservationListResponse(
                reservation.getId(),
                reservation.getCreatedAt(),
                reservation.getReservationNumber(),
                reservation.getStatus().name(),
                reservation.getBakery().getId(),
                reservation.getBakery().getName(),
                reservation.getTotalPrice(),
                reservation.getPickupDeadline(),
                totalCount,
                mainName
        );
    }
}
