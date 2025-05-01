package com.breaditnow.owner.domain.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.owner.domain.reservation.controller.req.ReservationStatusUpdateRequest;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.owner.domain.reservation.service.ReservationService;
import com.breaditnow.owner.global.security.annotation.AuthOwner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/owner")
@Slf4j
public class ReservationController implements ReservationControllerDocs {

	private final ReservationService reservationService;

	@GetMapping
	public ApiSuccessResponse<ReservationPageResponse> getReservationsForOwner(
		@AuthOwner Long ownerId,
		@RequestParam(name = "status", required = false, defaultValue = "ALL") ReservationRequestStatus status,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiSuccessResponse.of(reservationService.getReservationsForOwner(ownerId, status, page, size));
	}

	@GetMapping("/{reservationId}")
	public ApiSuccessResponse<ReservationDetailResponse> getReservationDetailForOwner(
		@AuthOwner Long ownerId,
		@PathVariable("reservationId") Long reservationId
	) {
		return ApiSuccessResponse.of(reservationService.getReservationDetailForOwner(ownerId, reservationId));
	}

	@PatchMapping("/{reservationId}/status")
	public ApiSuccessResponse<Void> updateReservationStatus(
		@AuthOwner Long ownerId,
		@PathVariable("reservationId") Long reservationId,
		@RequestBody ReservationStatusUpdateRequest request) {

		reservationService.updateReservationStatus(ownerId, reservationId, request);
		return ApiSuccessResponse.of();
	}
}
