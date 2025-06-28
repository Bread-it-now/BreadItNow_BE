package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.common.security.AuthenticatedUser;

public interface ApproveReservationUseCase {
    void approveReservation(AuthenticatedUser user, Long reservationId);
}
