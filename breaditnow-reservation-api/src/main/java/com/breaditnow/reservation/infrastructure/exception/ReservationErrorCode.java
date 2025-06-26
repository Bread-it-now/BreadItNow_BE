package com.breaditnow.reservation.infrastructure.exception;

import com.breaditnow.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ReservationErrorCode implements ErrorCode {
	/**
	 * EA000 Reservation
	 */
	RESERVATION_NOT_FOUND(NOT_FOUND, "EA001", "RESERVATION_NOT_FOUND"),
	PRODUCT_NOT_BELONG_TO_BAKERY(BAD_REQUEST, "EA002", "상품이 해당 빵집에 속하지 않습니다."),
	UNAUTHORIZED_RESERVATION_CANCEL(UNAUTHORIZED, "EA003", "예약 취소 권한이 없습니다."),
	CANCELLATION_REASON_REQUIRED(BAD_REQUEST, "EA004", "예약 취소 사유는 필수입니다."),
	ALREADY_CANCELLED(BAD_REQUEST, "EA005", "이미 취소된 예약입니다."),
	QUANTITY_POSITIVE(BAD_REQUEST, "EA006", "예약 상품의 수량은 0보다 커야 합니다."),
	BAKERY_NOT_FOUND(NOT_FOUND, "EA007", "해당 빵집을 찾을 수 없습니다."),
	BAKERY_NOT_AVAILABLE(BAD_REQUEST, "EA008", "예약이 불가능한 빵집입니다."),
	PRODUCT_NOT_FOUND(NOT_FOUND, "EA009", "존재하지 않는 상품입니다."),
	PRODUCT_NOT_AVAILABLE(BAD_REQUEST, "EA010", "예약이 불가능한 상품입니다."),
	ALREADY_PROCESSED(BAD_REQUEST, "EA011", "이미 처리된 예약입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ReservationErrorCode(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}

