package com.breaditnow.customer.reservation.infrastructure.jpa;

import com.breaditnow.customer.reservation.infrastructure.jpa.entity.ReservationStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationStatusHistoryRepository extends JpaRepository<ReservationStatusHistoryEntity, Long> {
}
