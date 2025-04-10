package com.breaditnow.owner.domain.reservation.controller.req;

import java.util.List;

import com.breaditnow.domain.domain.reservation.enumerate.ReservationUpdateStatus;

import jakarta.validation.constraints.NotNull;

public record ReservationStatusUpdateRequest(
	@NotNull ReservationUpdateStatus status,
	String reason,
	List<ReservationItemUpdateRequest> reservationItems
) {
}
