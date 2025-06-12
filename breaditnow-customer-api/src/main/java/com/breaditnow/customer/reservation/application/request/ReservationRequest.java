package com.breaditnow.customer.reservation.application.request;

import java.util.List;

public record ReservationRequest(
        Long bakeryId,
        List<ReservationProduct> reservationProducts
){
}
