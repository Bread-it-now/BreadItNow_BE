package com.breaditnow.reservation.adapter.out.persistence.repository;

import com.breaditnow.reservation.adapter.out.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
