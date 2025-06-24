package com.breaditnow.reservation.infrastructure.jpa.repository;

import com.breaditnow.reservation.infrastructure.jpa.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
