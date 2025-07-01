package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.domain.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    Long getNextReservationNumber(Long bakeryId);

    List<Reservation> findByCustomerId(Long customerId);
    Page<Reservation> findByCustomerId(Long customerId, Pageable pageable, ReservationStatus status);

    List<Reservation> findByBakeryId(Long bakeryId);
    Page<Reservation> findByBakeryId(Long bakeryId, Pageable pageable, ReservationStatus status);
}
