package com.breaditnow.customer.domain.reservation.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.reservation.controller.req.ReservationCancelRequest;
import com.breaditnow.customer.domain.reservation.controller.req.ReservationRequest;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationCancelResponse;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.customer.domain.reservation.controller.res.ReservationResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 예약 API")
public interface ReservationControllerDocs {
	@Operation(summary = "고객 ID와 예약 요청 데이터를 기반으로 새로운 예약을 생성합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, BAKERY_NOT_FOUND, PRODUCT_NOT_FOUND, BAKERY_MISMATCH,
		PRODUCT_CANNOT_ORDER, RESERVATION_OUT_OF_STOCK})
	ApiSuccessResponse<ReservationResponse> createReservation(Long customerId, ReservationRequest request);

	@Operation(summary = "고객 ID, 예약 ID 및 취소 요청 데이터를 기반으로 예약을 취소합니다.")
	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND, RESERVATION_ALREADY_CANCELLED})
	ApiSuccessResponse<ReservationCancelResponse> cancelReservation(Long customerId, Long reservationId,
		ReservationCancelRequest request);

	@Operation(summary = "고객 ID와 예약 요청 상태, 페이지 및 사이즈 정보를 기반으로 예약 목록을 조회합니다.")
	ApiSuccessResponse<ReservationPageResponse> getReservations(Long customerId, ReservationRequestStatus status,
		int page, int size);

	@Operation(summary = "고객 ID와 예약 ID를 기반으로 예약 상세 정보를 조회합니다.")
	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND})
	ApiSuccessResponse<ReservationDetailResponse> getReservationDetail(Long customerId, Long reservationId);
}
