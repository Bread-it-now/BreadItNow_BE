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
    private String reason;

    public ReservationState(ReservationStatus reservationStatus, String reason) {
        this.reservationStatus = reservationStatus;
        this.reason = reason;
    }

    public static ReservationState waiting() {
        return new ReservationState(WAITING, null);
    }

    public void approve() {
        validateNotWaiting();
        this.reservationStatus = APPROVED;
    }

    public void partiallyApprove(String reason) {
        validateNotWaiting();
        this.reservationStatus = PARTIAL_APPROVED;
        this.reason = reason;
    }

    public void cancel(String cancelReason) {
        requireValid(cancelReason, Objects::isNull, () -> new ReservationException(CANCELLATION_REASON_REQUIRED));
        this.reservationStatus = CANCELLED;
        this.reason = cancelReason;
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
