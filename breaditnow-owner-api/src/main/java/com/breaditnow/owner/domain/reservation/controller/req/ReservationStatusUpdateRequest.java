package com.breaditnow.owner.domain.reservation.controller.req;

import com.breaditnow.domain.domain.reservation.enumerate.ReservationUpdateStatus;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReservationStatusUpdateRequest(
        @NotNull ReservationUpdateStatus status,
        String reason,
        List<ReservationItemUpdateRequest> reservationItems
) {}