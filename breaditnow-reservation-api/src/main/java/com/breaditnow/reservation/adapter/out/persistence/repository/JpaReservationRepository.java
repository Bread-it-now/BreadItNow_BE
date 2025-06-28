package com.breaditnow.reservation.adapter.out.persistence.repository;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.out.persistence.entity.ReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ReservationEntity> findFirstByBakeryIdAndReservationStatusAndModifiedAtBetweenOrderByReservationNumberDesc(
            Long bakeryId,
            ReservationStatus status,
            LocalDateTime startOfTime,
            LocalDateTime endOfTime
    );
}
