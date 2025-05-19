package com.breaditnow.customer.reservation.controller.res;

import com.breaditnow.customer.bakery.controller.res.BakeryResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;

import java.util.List;

public record ReservationDetailResponse(
        BakeryResponse bakery,
        ReservationInfoResponse reservation
) {
    public static ReservationDetailResponse of(Reservation reservation, Bakery bakery, List<ReservationProduct> products) {
        return new ReservationDetailResponse(
                BakeryResponse.of(bakery),
                ReservationInfoResponse.of(reservation, products)
        );
    }
}
