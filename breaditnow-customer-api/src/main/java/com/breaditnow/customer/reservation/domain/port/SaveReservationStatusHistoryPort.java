package com.breaditnow.customer.reservation.domain.port;

import com.breaditnow.customer.reservation.domain.ReservationStatus;

public interface SaveReservationStatusHistoryPort {
    void save(Long reservationId, ReservationStatus oldReservationStatus, ReservationStatus newReservationStatus);
}
