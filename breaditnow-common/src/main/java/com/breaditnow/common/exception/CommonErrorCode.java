package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "AA01", "잘못된 파라미터가 포함되었습니다."),

	UNDEFINED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AA02", "정의되지 않은 에러입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

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
