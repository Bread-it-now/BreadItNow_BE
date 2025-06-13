package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.ALREADY_CANCELLED;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.CANCELLATION_REASON_REQUIRED;
import static com.breaditnow.customer.reservation.domain.ReservationStatus.CANCELLED;

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

    public void cancelled(String cancelReason) {
        requireValid(cancelReason, StringUtils::isEmpty, () -> new CustomerException(CANCELLATION_REASON_REQUIRED));
        requireValid(this.reservationStatus, status -> status == CANCELLED, () -> new CustomerException(ALREADY_CANCELLED));
        this.reservationStatus = CANCELLED;
        this.cancelReason = cancelReason;
    }
}
