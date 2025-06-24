package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.domain.Reservation;

import java.util.Optional;

public interface ReservationRepositoryPort {
    Optional<Reservation> findById(Long reservationId);
    Reservation save(Reservation reservation);
}
