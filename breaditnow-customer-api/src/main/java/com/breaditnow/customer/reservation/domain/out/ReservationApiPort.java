package com.breaditnow.customer.reservation.domain.out;

import com.breaditnow.customer.reservation.application.dto.request.ReservationCreateRequest;

public interface ReservationApiPort {
    Long createReservation(Long customerId, ReservationCreateRequest request);
}
