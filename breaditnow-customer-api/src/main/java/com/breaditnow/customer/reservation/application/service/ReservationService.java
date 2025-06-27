package com.breaditnow.customer.reservation.application.service;

import com.breaditnow.customer.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.customer.reservation.domain.in.CreateReservationUseCase;
import com.breaditnow.customer.reservation.domain.out.ReservationApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService implements CreateReservationUseCase {
    private final ReservationApiPort reservationApiPort;

    @Override
    public Long createReservation(Long customerId, ReservationCreateRequest request) {
        return reservationApiPort.createReservation(customerId, request);
    }
}
