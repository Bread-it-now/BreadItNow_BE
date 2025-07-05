package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.response.*;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.MyReservationQueryUseCase;
import com.breaditnow.reservation.domain.port.in.ReservationQueryUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryService implements ReservationQueryUseCase, MyReservationQueryUseCase {
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final ReservationRepository reservationRepository;
    private final ReservationProvider reservationProvider;
    private final ReservationValidator reservationValidator;

    @Override
    @Authorize(OWNER)
    public ReservationPageResponse getMyReservations(AuthenticatedUser user, Long bakeryId, Pageable pageable, ReservationStatus status) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        return ReservationPageResponse.from(reservationRepository.findByBakeryId(bakeryId, pageable, status));
    }

    @Override
    @Authorize(OWNER)
    public ReservationDetailResponse getReservation(AuthenticatedUser user, Long bakeryId, Long reservationId) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        return ReservationDetailResponse.from(reservationProvider.provide(reservationId, bakeryId));
    }

    @Override
    @Authorize(CUSTOMER)
    public MyReservationSimpleResponse getSimpleReservation(AuthenticatedUser user, Long reservationId) {
        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateReservationBelongsToCustomer(reservation, user.userId());
        return MyReservationSimpleResponse.from(reservation);
    }

    @Override
    @Authorize(CUSTOMER)
    public MyReservationDetailResponse getDetailReservation(AuthenticatedUser user, Long reservationId) {
        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateReservationBelongsToCustomer(reservation, user.userId());
        return MyReservationDetailResponse.from(reservation);
    }

    @Override
    @Authorize(CUSTOMER)
    public MyReservationPageResponse getMyReservations(AuthenticatedUser user, Pageable pageable, ReservationStatus status) {
        Page<Reservation> reservationPage = reservationRepository.findByCustomerId(user.userId(), pageable, status);
        return MyReservationPageResponse.from(reservationPage);
    }
}
