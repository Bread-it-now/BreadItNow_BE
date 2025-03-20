package com.breaditnow.domain.domain.reservation.enumerate;

public enum ReservationRequestStatus {
    ALL, WAITING, APPROVED, PARTIAL_APPROVED, CANCELLED, PAYMENT_COMPLETED;

    public static ReservationStatus toReservationStatus(ReservationRequestStatus requestStatus) {
        return (requestStatus == ALL) ? null : ReservationStatus.valueOf(requestStatus.name());
    }
}