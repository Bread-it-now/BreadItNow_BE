package com.breaditnow.reservation.domain.port.in;


import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCreateRequest;

public interface CreateReservationUseCase {
    Long createReservation(AuthenticatedUser user, ReservationCreateRequest request);
}

