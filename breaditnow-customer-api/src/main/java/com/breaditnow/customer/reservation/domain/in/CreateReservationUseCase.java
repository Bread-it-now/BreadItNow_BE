package com.breaditnow.customer.reservation.domain.in;

import com.breaditnow.customer.reservation.application.dto.request.ReservationCreateRequest;

public interface CreateReservationUseCase {
    Long createReservation(Long customerId, ReservationCreateRequest request);
}
