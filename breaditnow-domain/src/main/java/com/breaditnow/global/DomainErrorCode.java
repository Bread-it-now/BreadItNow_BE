package com.breaditnow.global;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum DomainErrorCode implements ErrorCode {
	/**
	 * Owner(BB000)
	 */
	OWNER_NOT_FOUND(NOT_FOUND, "BB001", "사업자를 찾을 수 없습니다."),

	/**
	 * Address(BC000)
	 */

	/**
	 * Region(BD000)
	 */
	Region_NOT_FOUND(NOT_FOUND, "BD001", "지역을 찾을 수 없습니다.");

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
