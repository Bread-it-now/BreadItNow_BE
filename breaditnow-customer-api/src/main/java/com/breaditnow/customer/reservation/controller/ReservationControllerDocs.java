package com.breaditnow.customer.reservation.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.reservation.controller.req.ReservationCancelRequest;
import com.breaditnow.customer.reservation.controller.req.ReservationRequest;
import com.breaditnow.customer.reservation.controller.res.ReservationCancelResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.customer.reservation.controller.res.ReservationResponse;
import com.breaditnow.customer.common.swagger.annotation.DomainErrorCodeExamples;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 예약 API", description = "예약 생성, 취소, 목록 조회, 상세 조회를 할 수 있는 API입니다.")
public interface ReservationControllerDocs {

	@Operation(summary = "예약 생성", description = "예약 정보를 RequestBody로 전달하여 새 예약을 생성합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, BAKERY_NOT_FOUND, PRODUCT_NOT_FOUND, BAKERY_MISMATCH,
		PRODUCT_CANNOT_ORDER, RESERVATION_OUT_OF_STOCK})
	ApiSuccessResponse<ReservationResponse> createReservation(Long customerId, ReservationRequest request);

	@Operation(summary = "예약 취소", description = "특정 예약(reservationId)을 취소합니다. RequestBody에 취소 관련 정보를 전달합니다.")
	@Parameter(name = "reservationId", description = "취소할 예약 ID", example = "2001", required = true)
	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND, RESERVATION_ALREADY_CANCELLED})
	ApiSuccessResponse<ReservationCancelResponse> cancelReservation(Long customerId, Long reservationId,
		ReservationCancelRequest request);

	@Operation(summary = "예약 목록 조회", description = "예약 목록을 페이지네이션으로 반환합니다.")
	@Parameters({
		@Parameter(name = "status", description = "예약 상태 필터", example = "ALL", in = QUERY),
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
		@Parameter(name = "size", description = "한 페이지당 예약 개수", example = "10", in = QUERY)
	})
	ApiSuccessResponse<ReservationPageResponse> getReservations(Long customerId, ReservationRequestStatus status,
		int page, int size);

	@Operation(summary = "예약 상세 조회", description = "특정 예약(reservationId)에 대한 상세 정보를 조회합니다.")
	@Parameter(name = "reservationId", description = "조회할 예약 ID", example = "2001", required = true)
	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND})
	ApiSuccessResponse<ReservationDetailResponse> getReservationDetail(Long customerId, Long reservationId);
}
