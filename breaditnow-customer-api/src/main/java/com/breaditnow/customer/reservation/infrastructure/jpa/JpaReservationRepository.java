package com.breaditnow.customer.reservation.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("select max(r.reservationNumber) from ReservationEntity r where r.bakeryId = :bakeryId and r.reservationTime >= :start and r.reservationTime < :end")
    Optional<Long> findLatestReservationNumberForBakeryToday(@Param("bakeryId") Long bakeryId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
