package com.breaditnow.reservation.domain.port.in;


import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;

public interface CreateReservationUseCase {
    Long createReservation(Long customerId, ReservationCreateRequest request);
}

