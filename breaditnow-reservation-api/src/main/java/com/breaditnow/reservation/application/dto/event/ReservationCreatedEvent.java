package com.breaditnow.reservation.application.dto.event;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.domain.Reservation;
import com.breaditnow.reservation.domain.ReservationProduct;
import com.breaditnow.reservation.domain.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationCreatedEvent(
        Long reservationId,
        Long bakeryId,
        Long userId,
        LocalDateTime reservationTime,
        ReservationStatus status,
        Money totalPrice,
        List<ProductInfo> products
) {
    public record ProductInfo(Long productId, Integer quantity) {
        public static ProductInfo from(ReservationProduct product) {
            return new ProductInfo(product.getProductId(), product.getQuantity());
        }
    }

    public static ReservationCreatedEvent from(Reservation reservation) {
        return new ReservationCreatedEvent(
                reservation.getReservationId(),
                reservation.getBakeryId(),
                reservation.getOrdererId(),
                reservation.getReservationTime(),
                reservation.getReservationState().getReservationStatus(),
                reservation.getTotalPrice(),
                reservation.getReservationProducts().stream().map(ProductInfo::from).toList()
        );
    }
}