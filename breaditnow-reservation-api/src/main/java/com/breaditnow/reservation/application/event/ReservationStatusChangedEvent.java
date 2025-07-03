package com.breaditnow.reservation.application.event;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.domain.Role;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationStatusChangedEvent(
        Long reservationId,
        Long bakeryId,
        Long ownerId,
        Long customerId,
        String bakeryName,
        String customerNickname,
        List<String> productNames,
        Integer totalPrice,
        ReservationStatus reservationStatus,
        Long reservationNumber,
        LocalDateTime pickupDeadline,
        Role initiatedBy,
        String cancelReason,
        LocalDateTime eventOccurredAt
) {
    public static ReservationStatusChangedEvent from(Reservation reservation, Role initiatedBy, String cancelReason, Long ownerId) {
        return new ReservationStatusChangedEvent(
                reservation.getReservationId(),
                reservation.getReservedBakery().bakeryId(),
                ownerId,
                reservation.getOrderer().getCustomerId(),
                reservation.getReservedBakery().name(),
                reservation.getOrderer().getNickname(),
                reservation.getReservationProducts().stream().map(ReservationProduct::getProductName).toList(),
                reservation.getTotalPrice().getAmount(),
                reservation.getReservationState().getReservationStatus(),
                reservation.getReservationNumber(),
                reservation.calculatePickupDeadline(),
                initiatedBy,
                cancelReason,
                LocalDateTime.now()
        );
    }
}
