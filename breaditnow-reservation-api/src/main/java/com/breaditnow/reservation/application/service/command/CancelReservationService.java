package com.breaditnow.reservation.application.service.command;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.application.service.strategy.ReservationCancelStrategy;
import com.breaditnow.reservation.application.service.strategy.ReservationCancelStrategyFactory;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.RESERVATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelReservationService implements CancelReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final ReservationCancelStrategyFactory strategyFactory;

    @Override
    public void cancelReservation(AuthenticatedUser user, Long reservationId, ReservationCancelRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        ReservationCancelStrategy strategy = strategyFactory.findStrategy(Role.fromString(user.role()));
        strategy.checkAuthority(user, reservation);
        reservation.cancel(request.reason());
    }
}
