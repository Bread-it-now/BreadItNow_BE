package com.breaditnow.reservation.domain;


import com.breaditnow.reservation.infrastructure.exception.ReservationException;
import lombok.Getter;

import java.util.Objects;

import static com.breaditnow.reservation.domain.ReservationStatus.CANCELLED;
import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.reservation.infrastructure.exception.ReservationErrorCode.ALREADY_CANCELLED;
import static com.breaditnow.reservation.infrastructure.exception.ReservationErrorCode.CANCELLATION_REASON_REQUIRED;

@Getter
public class ReservationState {
    private ReservationStatus reservationStatus;
    private String cancelReason;

    public ReservationState(ReservationStatus reservationStatus, String cancelReason) {
        this.reservationStatus = reservationStatus;
        this.cancelReason = cancelReason;
    }

    public static ReservationState waiting() {
        return new ReservationState(ReservationStatus.WAITING, null);
    }

    public void cancel(String cancelReason) {
        requireValid(cancelReason, o -> o == null | Objects.requireNonNull(o).isEmpty(), () -> new ReservationException(CANCELLATION_REASON_REQUIRED));
        requireValid(this.reservationStatus, status -> status == CANCELLED, () -> new ReservationException(ALREADY_CANCELLED));
        this.reservationStatus = CANCELLED;
        this.cancelReason = cancelReason;
    }
}
