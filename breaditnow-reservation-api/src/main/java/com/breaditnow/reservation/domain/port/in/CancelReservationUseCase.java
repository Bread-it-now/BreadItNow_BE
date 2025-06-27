package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;

public interface CancelReservationUseCase {
    void cancelReservation(Long userId, String roleString, Long reservationId, ReservationCancelRequest request);

}
