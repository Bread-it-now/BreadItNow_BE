package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;

public interface ApproveReservationUseCase {
    void approveReservation(AuthenticatedUser user, Long reservationId);
}
