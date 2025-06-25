package com.breaditnow.owner.reservation.infrastructure.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationViewRepository extends JpaRepository<ReservationViewEntity, Long> {
}
