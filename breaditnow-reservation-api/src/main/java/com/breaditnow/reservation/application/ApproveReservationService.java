package com.breaditnow.reservation.application;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.ApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ApproveReservationService implements ApproveReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final OwnerApiPort ownerApiPort;

    @Override
    public void approveReservation(AuthenticatedUser user, Long reservationId) {
        if(!user.isOwner()){
            throw new ReservationException(FORBIDDEN_ACCESS);
        }

        Reservation reservationToApprove = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        BakeryInfo bakeryInfo = ownerApiPort.findBakeryById(reservationToApprove.getReservedBakery().bakeryId())
                .orElseThrow(() -> new ReservationException(BAKERY_NOT_FOUND));

        if (!bakeryInfo.ownerId().equals(user.userId())) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }

        Long newReservationNumber = reservationRepository.findLastOfBakeryForToday(reservationToApprove.getReservedBakery().bakeryId())
                .map(lastReservation -> lastReservation.getReservationNumber() + 1)
                .orElse(1L);

        reservationToApprove.approve(newReservationNumber);
        reservationRepository.save(reservationToApprove);
    }
}
