package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.common.exception.ReservationException;
import lombok.Getter;

import java.util.Objects;

import static com.breaditnow.common.exception.ReservationErrorCode.*;
import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.common.domain.ReservationStatus.*;

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
        if(isCompleted()) {
            throw new ReservationException(ALREADY_PROCESSED);
        }

        requireValid(cancelReason, Objects::isNull, () -> new ReservationException(CANCELLATION_REASON_REQUIRED));
        this.reservationStatus = CANCELLED;
        this.cancelReason = cancelReason;
    }

    public boolean isCompleted() {
        return this.reservationStatus == APPROVED || this.reservationStatus == PARTIAL_APPROVED;
    }

    private void validateNotWaiting() {
        if (this.reservationStatus != WAITING) {
            throw new ReservationException(ALREADY_PROCESSED);
        }
    }
}
