package com.breaditnow.reservation.application;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.response.MySimpleReservationResponse;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.in.ReservationQueryUseCase;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.ReservationErrorCode.FORBIDDEN_ACCESS;
import static com.breaditnow.common.exception.ReservationErrorCode.RESERVATION_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService implements ReservationQueryUseCase {
    private final ReservationRepository reservationRepository;
    private final OwnerApiPort ownerApiPort;

    @Override
    public MySimpleReservationResponse getSimpleReservation(AuthenticatedUser user, Long reservationId) {
        if(!user.isCustomer()) {
            throw new ReservationException(FORBIDDEN_ACCESS);
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));

        BakeryInfo bakeryInfo = ownerApiPort.findBakeryById(reservation.getBakeryId())
                .orElseThrow(() -> new ReservationException(FORBIDDEN_ACCESS));

        return MySimpleReservationResponse.from(reservation, bakeryInfo);
    }
}
