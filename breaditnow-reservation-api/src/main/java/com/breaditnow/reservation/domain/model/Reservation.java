package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.exception.ReservationErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;

@Getter
public class Reservation {
    private Long reservationId;
    private ReservedBakery reservedBakery;
    private Long customerId;
    private Long reservationNumber;
    private List<ReservationProduct> reservationProducts;
    private ReservationState reservationState;
    private Money totalPrice;
    private LocalDateTime reservationTime;

    @Builder
    private Reservation(Long reservationId, Long reservationNumber, List<ReservationProduct> reservationProducts, ReservedBakery reservedBakery, Long customerId, ReservationState reservationState, Money totalPrice,  LocalDateTime reservationTime) {
        this.reservationId = reservationId;
        this.reservedBakery = reservedBakery;
        this.customerId = customerId;
        this.reservationNumber = reservationNumber;
        this.reservationProducts = reservationProducts;
        this.reservationState = reservationState;
        this.totalPrice = totalPrice;
        this.reservationTime = reservationTime;
    }

    public Reservation(Long customerId, ReservedBakery reservedBakery, List<ReservationProduct> reservationProducts) {
        this.customerId = customerId;
        this.reservedBakery = reservedBakery;
        this.reservationProducts = reservationProducts;
        this.reservationState = ReservationState.waiting();
        this.totalPrice = calculateTotalPrice();
    }

    public void approve(Long newReservationNumber){
        this.reservationState.approve();
        this.reservationNumber = newReservationNumber;
    }

    public void cancel(Long userId, Role role, String reason) {
        if(role == CUSTOMER && !userId.equals(this.customerId)) {
            throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
        }
        this.reservationState.cancel(reason);
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
