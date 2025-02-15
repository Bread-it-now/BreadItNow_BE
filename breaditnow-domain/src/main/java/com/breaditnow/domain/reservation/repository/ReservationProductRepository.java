package com.breaditnow.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.reservation.entity.ReservationProduct;

public interface ReservationProductRepository extends JpaRepository<ReservationProduct, Long> {

}

