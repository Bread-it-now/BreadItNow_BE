package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.response.MyReservationDetailResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationSimpleResponse;

public interface ReservationQueryUseCase {
    MyReservationSimpleResponse getSimpleReservation(AuthenticatedUser user, Long reservationId);
    MyReservationDetailResponse getDetailReservation(AuthenticatedUser user, Long reservationId);
}
