package com.breaditnow.domain.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationProductRepository extends JpaRepository<ReservationProduct, Long> {

    @Query("SELECT rp FROM ReservationProduct rp JOIN FETCH rp.product WHERE rp.reservation.id = :reservationId")
    List<ReservationProduct> findByReservationId(@Param("reservationId") Long reservationId);
}

