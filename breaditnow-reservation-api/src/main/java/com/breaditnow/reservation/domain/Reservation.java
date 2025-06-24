package com.breaditnow.reservation.domain;

import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.infrastructure.exception.ReservationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.reservation.infrastructure.exception.ReservationErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;


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
        requireValid(ordererId, id -> !getOrdererId().equals(id), () -> new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL));
        reservationState.cancel(reason);
    }

    private Money calculateTotalPrice() {
        return reservationProducts.stream()
                .map(ReservationProduct::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }
}
