package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.domain.model.Reservation;

import java.util.Optional;

public interface ReservationRepository {
    Optional<Reservation> findById(Long reservationId);
    Reservation save(Reservation reservation);
}
