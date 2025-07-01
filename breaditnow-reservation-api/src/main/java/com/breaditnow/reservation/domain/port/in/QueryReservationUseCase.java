package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.response.ReservationPageResponse;
import org.springframework.data.domain.Pageable;

public interface QueryReservationUseCase {
    ReservationPageResponse getMyReservations(AuthenticatedUser user, Long bakeryId, Pageable pageable, ReservationStatus status);
}
