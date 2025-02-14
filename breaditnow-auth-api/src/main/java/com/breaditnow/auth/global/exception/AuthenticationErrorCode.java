package com.breaditnow.auth.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum AuthenticationErrorCode implements ErrorCode {
	INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "BA001", "잘못된 자격 증명입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	AuthenticationErrorCode(HttpStatus httpStatus, String code, String message) {
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

