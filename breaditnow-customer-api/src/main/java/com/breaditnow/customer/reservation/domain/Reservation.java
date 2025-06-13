package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;

@Getter
public class Reservation {
    private Long reservationId;
    private Long bakeryId;
    private Long reservationNumber;
    private List<ReservationItem> reservationItems;
    private Orderer orderer;
    private ReservationState reservationState;
    private LocalDateTime reservationTime;
    private Money totalPrice;

    @Builder
    private Reservation(Long reservationId, Long reservationNumber, List<ReservationItem> reservationItems, Long bakeryId, Orderer orderer, ReservationStatus reservationStatus, LocalDateTime reservationTime, Money totalPrice, String cancellationReason) {
        this.reservationId = reservationId;
        this.bakeryId = bakeryId;
        this.reservationNumber = reservationNumber;
        this.reservationItems = reservationItems;
        this.orderer = orderer;
        this.reservationTime = reservationTime;
        this.totalPrice = totalPrice;
        this.reservationState = new ReservationState(reservationStatus, cancellationReason);
    }

    public Reservation(Orderer orderer, Long bakeryId, List<ReservationItem> reservationItems) {
        this.orderer = orderer;
        this.bakeryId = bakeryId;
        this.reservationItems = reservationItems;
        this.reservationState = ReservationState.waiting();
        this.reservationTime = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
    }

    public void cancel(Long customerId, String reason) {
        requireValid(customerId, id -> !orderer.getOrdererId().equals(id), () -> new CustomerException(UNAUTHORIZED_RESERVATION_CANCEL));
        reservationState.cancelled(reason);
    }

    private Money calculateTotalPrice() {
        return reservationItems.stream()
                .map(ReservationItem::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }
}
