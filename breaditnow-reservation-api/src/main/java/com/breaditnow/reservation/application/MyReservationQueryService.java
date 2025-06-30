package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.response.MyReservationDetailResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationPageResponse;
import com.breaditnow.reservation.application.dto.response.MyReservationSimpleResponse;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.MyReservationQueryUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyReservationQueryService implements MyReservationQueryUseCase {
    private final ReservationValidator reservationValidator;
    private final ReservationProvider reservationProvider;
    private final ReservationRepository reservationRepository;

    @Override
    public MyReservationSimpleResponse getSimpleReservation(AuthenticatedUser user, Long reservationId) {
        if(!user.isCustomer()) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }

        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateReservationBelongsToCustomer(reservation, user.userId());
        return MyReservationSimpleResponse.from(reservation);
    }

    @Override
    public MyReservationDetailResponse getDetailReservation(AuthenticatedUser user, Long reservationId) {
        if(!user.isCustomer()) {
            throw new ReservationException(FORBIDDEN_ACCESS);
        }

        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateReservationBelongsToCustomer(reservation, user.userId());

        return MyReservationDetailResponse.from(reservation);
    }

    @Override
    public MyReservationPageResponse getMyReservations(AuthenticatedUser user, Pageable pageable, ReservationStatus status) {
        if(!user.isCustomer()) {
            throw new ReservationException(FORBIDDEN_ACCESS);
        }

        Page<Reservation> reservationPage = reservationRepository.findByCustomerId(user.userId(), pageable, status);
        return MyReservationPageResponse.from(reservationPage);
    }
}
