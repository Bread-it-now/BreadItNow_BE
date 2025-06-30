package com.breaditnow.reservation.application.validator;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.domain.model.Reservation;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.ReservationErrorCode.UNAUTHORIZED_ACCESS;

@Component
public class ReservationValidator {
    public void validateReservationBelongsToCustomer(Reservation reservation, Long customerId) {
        if (!reservation.getCustomerId().equals(customerId)) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }
    }

    public void validateBakeryMatch(Reservation reservation, Long bakeryId) {
        if (!reservation.getReservedBakery().bakeryId().equals(bakeryId)) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }
    }
}
