package com.breaditnow.reservation.application;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.request.MyReservationCancelRequest;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.MyCancelReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MyCancelReservationService implements MyCancelReservationUseCase {
    private final ReservationRepository reservationRepository;

    @Override
    public void cancelReservation(AuthenticatedUser user, Long reservationId, MyReservationCancelRequest request) {
        if(!user.isCustomer()) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        if (!reservation.getCustomerId().equals(user.userId())) {
            throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
        }

        reservation.cancel(request.reason());
        reservationRepository.save(reservation);
    }
}
