package com.breaditnow.customer.reservation.presentation.request;

public record ReservationSearchRequest(
        Integer size,
        Integer page,
        String status
) {
}
