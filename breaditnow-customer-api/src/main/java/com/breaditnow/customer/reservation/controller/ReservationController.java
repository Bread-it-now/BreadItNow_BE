/*
package com.breaditnow.customer.reservation.controller;

import com.breaditnow.customer.common.presentation.swagger.docs.ReservationControllerDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.reservation.controller.req.ReservationCancelRequest;
import com.breaditnow.customer.reservation.controller.req.ReservationRequest;
import com.breaditnow.customer.reservation.controller.res.ReservationCancelResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationResponse;
import com.breaditnow.customer.reservation.service.ReservationService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
@Slf4j
public class ReservationController implements ReservationControllerDocs {

	private final ReservationService reservationService;

	@PostMapping
	public ApiSuccessResponse<ReservationResponse> createReservation(
		@AuthCustomer Long customerId,
		@RequestBody @Valid ReservationRequest request) {
		return ApiSuccessResponse.of(reservationService.createReservation(customerId, request));
	}

	@PatchMapping("/{reservationId}/cancel")
	public ApiSuccessResponse<ReservationCancelResponse> cancelReservation(
		@AuthCustomer Long customerId,
		@PathVariable("reservationId") Long reservationId,
		@RequestBody @Valid ReservationCancelRequest request) {
		return ApiSuccessResponse.of(reservationService.cancelReservation(customerId, reservationId, request));
	}

	@GetMapping
	public ApiSuccessResponse<ReservationPageResponse> getReservations(
		@AuthCustomer Long customerId,
		@RequestParam(bakeryName = "status", defaultValue = "ALL") ReservationRequestStatus status,
		@RequestParam(bakeryName = "page", defaultValue = "0") int page,
		@RequestParam(bakeryName = "size", defaultValue = "10") int size
	) {
		return ApiSuccessResponse.of(reservationService.getReservations(customerId, status, page, size));
	}

	@GetMapping("/{reservationId}")
	public ApiSuccessResponse<ReservationDetailResponse> getReservationDetail(
		@AuthCustomer Long customerId,
		@PathVariable("reservationId") Long reservationId) {
		return ApiSuccessResponse.of(reservationService.getReservationDetail(customerId, reservationId));
	}
}
*/
