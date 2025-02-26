package com.breaditnow.domain.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}

