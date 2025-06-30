package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.ApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional
public class ApproveReservationService implements ApproveReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final ReservationProvider reservationProvider;
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final ReservationValidator reservationValidator;

    @Override
    @Authorize(OWNER)
    public void approveReservation(AuthenticatedUser user, Long bakeryId, Long reservationId) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);

        Reservation reservationToApprove = reservationProvider.provide(reservationId);
        reservationValidator.validateBakeryMatch(reservationToApprove, bakeryId);

        Long newReservationNumber = reservationRepository.getNextReservationNumber(bakeryId);
        reservationToApprove.approve(newReservationNumber);
        reservationRepository.save(reservationToApprove);
    }
}
