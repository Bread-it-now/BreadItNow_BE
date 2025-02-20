package com.breaditnow.global;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum DomainErrorCode implements ErrorCode {
	OWNER_NOT_FOUND(HttpStatus.BAD_REQUEST, "DA001", "?");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	DomainErrorCode(HttpStatus httpStatus, String code, String message) {
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
