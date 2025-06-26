package com.breaditnow.reservation.domain;


import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.infrastructure.exception.ReservationException;
import lombok.Getter;

import java.util.Objects;

import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.common.domain.ReservationStatus.*;
import static com.breaditnow.reservation.infrastructure.exception.ReservationErrorCode.*;

@Getter
public class ReservationState {
    private ReservationStatus reservationStatus;
    private String cancelReason;

    public ReservationState(ReservationStatus reservationStatus, String cancelReason) {
        this.reservationStatus = reservationStatus;
        this.cancelReason = cancelReason;
    }

    public static ReservationState waiting() {
        return new ReservationState(WAITING, null);
    }

    public void approve() {
        validateNotWaiting();
        this.reservationStatus = APPROVED;
    }

    public void partiallyApprove() {
        validateNotWaiting();
        this.reservationStatus = PARTIAL_APPROVED;
    }

    public void cancel(String cancelReason) {
        requireValid(cancelReason, Objects::isNull, () -> new ReservationException(CANCELLATION_REASON_REQUIRED));
        requireValid(this.reservationStatus, status -> status == CANCELLED, () -> new ReservationException(ALREADY_CANCELLED));
        this.reservationStatus = CANCELLED;
        this.cancelReason = cancelReason;
    }

    private void validateNotWaiting() {
        if (this.reservationStatus != WAITING) {
            throw new ReservationException(ALREADY_PROCESSED);
        }
    }
}
