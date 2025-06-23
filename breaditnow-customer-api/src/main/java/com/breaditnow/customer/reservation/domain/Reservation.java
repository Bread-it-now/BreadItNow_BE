package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Events;
import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.reservation.domain.event.ReservationStatusChangedEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;
import static com.breaditnow.customer.reservation.domain.ReservationStatus.CANCELLED;

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

    public void cancel(Long ordererId, String reason) {
        requireValid(ordererId, id -> !getOrdererId().equals(id), () -> new CustomerException(UNAUTHORIZED_RESERVATION_CANCEL));
        ReservationStatus previousStatus = reservationState.getReservationStatus();
        reservationState.cancel(reason);
        Events.publish(new ReservationStatusChangedEvent(reservationId, previousStatus, CANCELLED));
    }

    private Money calculateTotalPrice() {
        return reservationProducts.stream()
                .map(ReservationProduct::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }
}
