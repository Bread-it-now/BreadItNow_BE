package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;

public interface PartialApproveReservationUseCase {
    void partialApproveReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationPartialApproveRequest request);
}
