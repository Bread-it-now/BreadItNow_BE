package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;

public interface CancelReservationUseCase {
    void cancelReservation(AuthenticatedUser user, Long reservationId, ReservationCancelRequest request);

}
