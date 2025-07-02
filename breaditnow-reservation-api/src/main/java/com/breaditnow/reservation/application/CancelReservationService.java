package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelReservationService implements CancelReservationUseCase {
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final ReservationProvider reservationProvider;
    private final ReservationValidator reservationValidator;
    private final ReservationRepository reservationRepository;

    @Override
    @Authorize(OWNER)
    public void cancelReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationCancelRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);

        Reservation reservation = reservationProvider.provide(reservationId, bakeryId);

        reservation.cancel(request.reason());
        reservationRepository.save(reservation);
    }
}
