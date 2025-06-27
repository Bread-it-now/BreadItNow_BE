package com.breaditnow.reservation.application.service.command;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
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

    @Override
    public void cancelReservation(Long userId, String roleString, Long reservationId, ReservationCancelRequest request) {
        Role role = Role.fromString(roleString);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
        reservation.cancel(userId, role, request.reason());
        reservationRepository.save(reservation);
    }
}
