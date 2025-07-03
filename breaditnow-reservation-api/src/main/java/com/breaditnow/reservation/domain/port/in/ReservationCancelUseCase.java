package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;

public interface ReservationCancelUseCase {
    void cancelReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationCancelRequest request);
}
