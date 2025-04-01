package com.breaditnow.owner.domain.reservation.controller.res;

public record ReservationStatusUpdateResponse(
        Long reservationId,
        String status
) {
    public static ReservationStatusUpdateResponse of(Long reservationId, String status) {
        return new ReservationStatusUpdateResponse(reservationId, status);
    }
}
