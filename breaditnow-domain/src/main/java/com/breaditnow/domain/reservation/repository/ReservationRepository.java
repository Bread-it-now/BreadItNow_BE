package com.breaditnow.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}

