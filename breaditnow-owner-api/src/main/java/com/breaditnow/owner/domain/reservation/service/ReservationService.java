package com.breaditnow.owner.domain.reservation.service;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.domain.domain.reservation.repository.ReservationRepository;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationPageResponse getReservationsForOwner(Long ownerId, ReservationRequestStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservations = reservationRepository.getReservationsByOwnerAndStatus(ownerId, status, pageable);
        return ReservationPageResponse.of(reservations);
    }
}
