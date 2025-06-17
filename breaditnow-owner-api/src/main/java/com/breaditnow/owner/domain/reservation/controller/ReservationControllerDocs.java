//package com.breaditnow.owner.domain.reservation.controller;
//
//import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
//import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;
//
//import com.breaditnow.common.response.ApiSuccessResponse;
//import com.breaditnow.domain.domain.reservation.enumerate.ReservationRequestStatus;
//import com.breaditnow.owner.domain.reservation.controller.req.ReservationStatusUpdateRequest;
//import com.breaditnow.owner.domain.reservation.controller.res.ReservationDetailResponse;
//import com.breaditnow.owner.domain.reservation.controller.res.ReservationPageResponse;
//import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//@Tag(name = "Owner - 예약 API", description = "소유자를 위한 예약 관리 API입니다. 예약 목록/상세 조회 및 상태 변경 등을 수행합니다.")
//public interface ReservationControllerDocs {
//
//	@Operation(
//		summary = "소유자 예약 목록 조회",
//		description = """
//			소유자 아이디(ownerId)에 대한 예약 목록을 페이지네이션 형태로 조회합니다.<br/>
//			status(ALL, WAITING, APPROVED 등)는 선택적으로 필터링 가능하며,<br/>
//			page와 size를 통해 페이지네이션을 조절할 수 있습니다.
//			"""
//	)
//	@Parameters({
//		@Parameter(name = "status", description = "예약 상태 필터 (기본값: ALL)", example = "REQUESTED", in = QUERY),
//		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
//		@Parameter(name = "size", description = "한 페이지당 노출될 데이터 개수", example = "10", in = QUERY)
//	})
//	ApiSuccessResponse<ReservationPageResponse> getReservationsForOwner(Long ownerId, ReservationRequestStatus status,
//		int page, int size);
//
//	@Operation(summary = "예약 상세 조회", description = "특정 예약(reservationId)의 상세 정보를 조회합니다.")
//	@Parameter(name = "reservationId", description = "상세 조회할 예약 ID", example = "11", required = true)
//	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND})
//	ApiSuccessResponse<ReservationDetailResponse> getReservationDetailForOwner(Long ownerId, Long reservationId);
//
//	@Operation(summary = "예약 상태 변경", description = "특정 예약 ID에 대해 승인/거부 등을 처리합니다.")
//	@Parameter(name = "reservationId", description = "상태 변경할 예약 ID", example = "1001", required = true)
//	@DomainErrorCodeExamples({RESERVATION_NOT_FOUND})
//	ApiSuccessResponse<Void> updateReservationStatus(Long ownerId, Long reservationId,
//		ReservationStatusUpdateRequest request);
//}
