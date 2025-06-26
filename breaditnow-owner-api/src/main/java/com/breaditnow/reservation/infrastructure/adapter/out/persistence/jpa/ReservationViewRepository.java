package com.breaditnow.reservation.infrastructure.adapter.out.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationViewRepository extends JpaRepository<ReservationViewEntity, Long> {
    Page<ReservationViewEntity> findByBakeryId(Long bakeryId, Pageable pageable);
    Page<ReservationViewEntity> findByBakeryIdAndStatus(Long bakeryId, String status, Pageable pageable);
}
