package com.breaditnow.domain.domain.reservation.repository;

import com.breaditnow.domain.domain.reservation.entity.ReservationSequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationSequenceRepository extends JpaRepository<ReservationSequence, LocalDate> {
}
