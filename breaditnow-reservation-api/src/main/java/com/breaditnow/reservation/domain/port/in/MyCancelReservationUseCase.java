package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.MyReservationCancelRequest;

public interface MyCancelReservationUseCase {
    void cancelReservation(AuthenticatedUser user, Long reservationId, MyReservationCancelRequest request);
}
