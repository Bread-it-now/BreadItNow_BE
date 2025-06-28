package com.breaditnow.reservation.application.service.command;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.request.ReservationCancelRequest;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.CancelReservationUseCase;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelReservationService implements CancelReservationUseCase {
    private final OwnerApiPort ownerApiPort;
    private final ReservationRepository reservationRepository;

    @Override
    public void cancelReservation(AuthenticatedUser user, Long reservationId, ReservationCancelRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        if (user.isCustomer()) {
            if(!reservation.getCustomerId().equals(user.userId())){
                throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
            }
            reservation.cancel(request.reason());
        }
        else if (user.isOwner()) {
            BakeryInfo bakeryInfo = ownerApiPort.findBakeryById(reservation.getBakeryId())
                    .orElseThrow(() -> new ReservationException(BAKERY_NOT_FOUND));

            if (!bakeryInfo.ownerId().equals(user.userId())) {
                throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
            }
            reservation.cancel(request.reason());
        }
        else {
            throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
        }
        reservationRepository.save(reservation);
    }
}
