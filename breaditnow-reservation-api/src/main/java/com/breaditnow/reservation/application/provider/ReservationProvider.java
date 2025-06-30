package com.breaditnow.reservation.application.provider;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.ReservationErrorCode.RESERVATION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ReservationProvider {
    private final ReservationRepository reservationRepository;

    public Reservation provide(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
    }
}
