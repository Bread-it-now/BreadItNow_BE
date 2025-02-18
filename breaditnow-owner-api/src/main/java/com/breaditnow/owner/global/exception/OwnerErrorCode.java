package com.breaditnow.owner.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum OwnerErrorCode implements ErrorCode {
	INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "DA001", "?");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	OwnerErrorCode(HttpStatus httpStatus, String code, String message) {
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

