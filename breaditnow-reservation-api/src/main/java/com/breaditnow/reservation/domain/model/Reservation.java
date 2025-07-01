package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.domain.Money;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Reservation {
    private Long reservationId;
    private ReservedBakery reservedBakery;
    private Orderer orderer;
    private Long reservationNumber;
    private List<ReservationProduct> reservationProducts;
    private ReservationState reservationState;
    private Money totalPrice;
    private LocalDateTime reservationTime;

    @Builder
    private Reservation(Long reservationId, Long reservationNumber, List<ReservationProduct> reservationProducts, ReservedBakery reservedBakery, Orderer orderer, ReservationState reservationState, Money totalPrice,  LocalDateTime reservationTime) {
        this.reservationId = reservationId;
        this.reservedBakery = reservedBakery;
        this.orderer = orderer;
        this.reservationNumber = reservationNumber;
        this.reservationProducts = reservationProducts;
        this.reservationState = reservationState;
        this.totalPrice = totalPrice;
        this.reservationTime = reservationTime;
    }

    public Reservation(Orderer orderer, ReservedBakery reservedBakery, List<ReservationProduct> reservationProducts) {
        this.orderer = orderer;
        this.reservedBakery = reservedBakery;
        this.reservationProducts = reservationProducts;
        this.reservationState = ReservationState.waiting();
        this.totalPrice = calculateTotalPrice();
    }

    public void approve(Long newReservationNumber){
        this.reservationState.approve();
        this.reservationNumber = newReservationNumber;
    }

    public void partialApprove(List<ReservationProduct> adjustedProducts, Long newReservationNumber) {
        this.reservationState.partiallyApprove();
        this.reservationProducts = adjustedProducts;
        this.reservationNumber = newReservationNumber;
        this.totalPrice = calculateTotalPrice();
    }

    private Money calculateTotalPrice() {
        return reservationProducts.stream()
                .map(ReservationProduct::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }

    public LocalDateTime calculatePickupDeadline() {
        if(reservationTime == null) {
            return null;
        }
        return reservationTime.plusMinutes(30);
    }

    public void cancel(String reason) {
        this.reservationState.cancel(reason);
    }
}
