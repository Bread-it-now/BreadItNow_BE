package com.breaditnow.customer.reservation.domain.port;

import com.breaditnow.customer.reservation.domain.Reservation;

public interface LoadReservationPort {
    Reservation getReservation(Long reservationId);
}
