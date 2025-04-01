package com.breaditnow.owner.domain.reservation.service;

import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.domain.domain.reservation.repository.ReservationProductRepository;
import com.breaditnow.domain.domain.reservation.repository.ReservationRepository;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationProductRepository reservationProductRepository;

    public ReservationPageResponse getReservationsForOwner(Long ownerId, ReservationRequestStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservations = reservationRepository.getReservationsByOwnerAndStatus(ownerId, status, pageable);
        return ReservationPageResponse.of(reservations);
    }

    public ReservationDetailResponse getReservationDetailForOwner(Long ownerId, Long reservationId) {
        Reservation reservation = reservationRepository.getByIdAndOwnerId(reservationId, ownerId);
        List<ReservationProduct> products = reservationProductRepository.findByReservationId(reservationId);
        return ReservationDetailResponse.of(reservation, reservation.getBakery(), products);
    }
}
