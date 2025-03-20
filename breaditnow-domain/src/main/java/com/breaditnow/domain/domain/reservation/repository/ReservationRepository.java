package com.breaditnow.domain.domain.reservation.repository;

import com.breaditnow.domain.global.exception.DomainException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.reservation.entity.Reservation;

import static com.breaditnow.domain.global.exception.DomainErrorCode.RESERVATION_NOT_FOUND;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    default Reservation getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(RESERVATION_NOT_FOUND));
    }
}

