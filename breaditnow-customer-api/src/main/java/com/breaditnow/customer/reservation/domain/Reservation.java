package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Money;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Reservation {
    private Long reservationId;
    private Long reservationNumber;
    private List<ReservationItem> reservationItems;
    private Long bakeryId;
    private Orderer orderer;
    private ReservationStatus reservationStatus;
    private LocalDateTime reservationTime;
    private LocalDateTime receivedTime;
    private Money totalPrice;

    public Reservation(Long reservationNumber, Orderer orderer, Long bakeryId, List<ReservationItem> reservationItems) {
        this.reservationNumber = reservationNumber;
        this.orderer = orderer;
        this.bakeryId = bakeryId;
        this.reservationItems = reservationItems;

        this.reservationStatus = ReservationStatus.WAITING;
        this.reservationTime = LocalDateTime.now();
        this.totalPrice = getTotalPrice();
    }

    public Money getTotalPrice() {
        return reservationItems.stream()
                .map(ReservationItem::getTotalPrice)
                .reduce(Money.ZERO, Money::add);
    }
}
