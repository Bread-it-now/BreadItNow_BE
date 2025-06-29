package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.response.MySimpleReservationResponse;

public interface ReservationQueryUseCase {
    MySimpleReservationResponse getSimpleReservation(AuthenticatedUser user, Long reservationId);
}
