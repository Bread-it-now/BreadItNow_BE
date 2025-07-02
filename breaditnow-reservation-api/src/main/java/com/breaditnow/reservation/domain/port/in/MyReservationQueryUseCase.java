package com.breaditnow.reservation.domain.port.in;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.response.MyReservationDetailResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationPageResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationSimpleResponse;
import org.springframework.data.domain.Pageable;

public interface MyReservationQueryUseCase {
    MyReservationSimpleResponse getSimpleReservation(AuthenticatedUser user, Long reservationId);
    MyReservationDetailResponse getDetailReservation(AuthenticatedUser user, Long reservationId);
    MyReservationPageResponse getMyReservations(AuthenticatedUser user, Pageable pageable, ReservationStatus status);

}
