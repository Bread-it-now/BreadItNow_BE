package com.breaditnow.owner.domain.reservation.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
import com.breaditnow.owner.domain.reservation.controller.req.ReservationStatusUpdateRequest;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationDetailResponse;
import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 예약 API")
public interface ReservationControllerDocs {
	@Operation(summary = "소유자 ID와 예약 상태, 페이지 정보로 예약 목록을 조회합니다.")
	ApiSuccessResponse<ReservationPageResponse> getReservationsForOwner(Long ownerId, ReservationRequestStatus status,
		int page, int size);

	@Operation(summary = "소유자 ID와 예약 ID를 기반으로 예약 상세 정보를 조회합니다.")
	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND})
	ApiSuccessResponse<ReservationDetailResponse> getReservationDetailForOwner(
		Long ownerId,
		Long reservationId
	);

	@Operation(summary = "소유자 ID와 예약 ID, 상태 업데이트 요청 정보를 기반으로 예약 상태를 업데이트합니다.")
	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND})
	ApiSuccessResponse<Void> updateReservationStatus(Long ownerId, Long reservationId,
		ReservationStatusUpdateRequest request);
}
