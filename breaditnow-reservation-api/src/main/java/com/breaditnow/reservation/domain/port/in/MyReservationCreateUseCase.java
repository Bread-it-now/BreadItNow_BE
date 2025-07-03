package com.breaditnow.reservation.domain.port.in;


import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.MyReservationCreateRequest;

public interface MyReservationCreateUseCase {
    Long createReservation(AuthenticatedUser user, MyReservationCreateRequest request);
}

