package com.breaditnow.reservation.adapter.out.persistence.repository;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.out.persistence.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface JpaReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Lock(PESSIMISTIC_WRITE)
    Optional<ReservationEntity> findFirstByBakeryIdAndReservationStatusAndModifiedAtBetweenOrderByReservationNumberDesc(Long bakeryId, ReservationStatus status, LocalDateTime startOfTime, LocalDateTime endOfTime);

    @Query("SELECT r FROM ReservationEntity r JOIN FETCH r.reservationItems WHERE r.ordererId = :customerId ORDER BY r.modifiedAt DESC")
    List<ReservationEntity> findAllByOrdererIdWithItemsOrderByModifiedAtDesc(@Param("customerId") Long customerId);

    @Query("SELECT r FROM ReservationEntity r JOIN FETCH r.reservationItems WHERE r.bakeryId = :bakeryId ORDER BY r.modifiedAt DESC")
    List<ReservationEntity> findAllByBakeryIdWithItemsOrderByModifiedAtDesc(@Param("bakeryId") Long bakeryId);

    @Query("SELECT r FROM ReservationEntity r " +
            "WHERE r.ordererId = :ordererId " +
            "AND (:status IS NULL OR r.reservationStatus = :status)")
    Page<ReservationEntity> findByOrdererId(Long ordererId, Pageable pageable, @Param("status") ReservationStatus status);
}
