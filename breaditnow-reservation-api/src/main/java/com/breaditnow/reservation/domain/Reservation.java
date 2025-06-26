package com.breaditnow.reservation.domain;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Reservation {
    private Long reservationId;
    private Long bakeryId;
    private Long reservationNumber;
    private List<ReservationProduct> reservationProducts;
    private Long ordererId;
    private ReservationState reservationState;
    private LocalDateTime reservationTime;
    private Money totalPrice;
    private LocalDateTime pickupDeadline;

    @Builder
    private Reservation(Long reservationId, Long reservationNumber, List<ReservationProduct> reservationProducts, Long bakeryId, Long ordererId, ReservationStatus reservationStatus, LocalDateTime reservationTime, Money totalPrice, String cancellationReason) {
        this.reservationId = reservationId;
        this.bakeryId = bakeryId;
        this.reservationNumber = reservationNumber;
        this.reservationProducts = reservationProducts;
        this.ordererId = ordererId;
        this.reservationTime = reservationTime;
        this.totalPrice = totalPrice;
        this.reservationState = new ReservationState(reservationStatus, cancellationReason);
    }

    public Reservation(Long ordererId, Long bakeryId, List<ReservationProduct> reservationProducts) {
        this.ordererId = ordererId;
        this.bakeryId = bakeryId;
        this.reservationProducts = reservationProducts;
        this.reservationState = ReservationState.waiting();
        this.reservationTime = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
    }

    public void approve(Long newReservationNumber){
        this.reservationState.approve();
        this.reservationNumber = newReservationNumber;
        this.pickupDeadline = calculatePickupDeadline();
    }

    public void cancel(String reason) {
        this.reservationState.cancel(reason);
    }

    public void partiallyApprove(List<ReservationProduct> adjustedProducts, Long newReservationNumber) {
        this.reservationState.partiallyApprove();
        this.reservationProducts = adjustedProducts;
        this.reservationNumber = newReservationNumber;
        this.pickupDeadline = calculatePickupDeadline();
        this.totalPrice = calculateTotalPrice();
    }

    private Money calculateTotalPrice() {
        return reservationProducts.stream()
                .map(ReservationProduct::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }

    private LocalDateTime calculatePickupDeadline() { return LocalDateTime.now().plusMinutes(30); }
}
