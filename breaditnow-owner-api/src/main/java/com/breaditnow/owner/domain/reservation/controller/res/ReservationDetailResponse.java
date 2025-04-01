package com.breaditnow.owner.domain.reservation.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.reservation.entity.Reservation;
import com.breaditnow.domain.domain.reservation.entity.ReservationProduct;
import com.breaditnow.owner.domain.bakery.controller.res.BakeryResponse;

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
