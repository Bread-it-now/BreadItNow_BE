package com.breaditnow.customer.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum CustomerErrorCode implements ErrorCode {
	/**
	 * CY000 인증
	 */
	AUTHENTICATION_REQUIRED(UNAUTHORIZED, "CY001", "Customer 인증 정보가 필수입니다."),
	ALERT_NOT_FOUND(NOT_FOUND, "CY002", "등록된 알람이 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	CustomerErrorCode(HttpStatus httpStatus, String code, String message) {
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

