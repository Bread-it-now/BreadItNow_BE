package com.breaditnow.reservation.application.port.in;

import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.reservation.application.dto.response.ReservationCreateResponse;

public interface CreateReservationUseCase {
    ReservationCreateResponse create(Long customerId, ReservationCreateRequest request);
}

