package com.breaditnow.reservation.application.event;

import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.reservation.domain.model.ReservationProduct;

import java.util.List;

public record ReservationPartiallyApprovedEvent(
        Long reservationId,
        UserIdentifier initiator,
        List<ReservationProduct> reservationProducts,
        String reason
) {}

