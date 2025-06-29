package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    Optional<Reservation> findLastOfBakeryForToday(Long bakeryId);

    List<Reservation> findByCustomerId(Long customerId);
    List<Reservation> findByBakeryId(Long bakeryId);
}
