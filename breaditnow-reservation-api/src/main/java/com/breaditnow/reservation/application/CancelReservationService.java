package com.breaditnow.reservation.application;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.UNAUTHORIZED_ACCESS;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelReservationService implements CancelReservationUseCase {
    private final ReservationProvider reservationProvider;
    private final ReservationValidator reservationValidator;
    private final ReservationRepository reservationRepository;

    @Override
    public void cancelReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationCancelRequest request) {
        if(!user.isOwner()) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }

        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateBakeryMatch(reservation, bakeryId);

        reservation.cancel(request.reason());
        reservationRepository.save(reservation);
    }
}
