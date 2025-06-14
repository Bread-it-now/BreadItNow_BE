package com.breaditnow.customer.reservation.domain.event;

import com.breaditnow.customer.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationStatusChangedEvent {
    private final Long reservationId;
    private final ReservationStatus previousStatus;
    private final ReservationStatus currentStatus;
}
