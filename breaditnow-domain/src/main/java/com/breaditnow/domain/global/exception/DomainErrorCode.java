package com.breaditnow.domain.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum DomainErrorCode implements ErrorCode {
	/**
	 * Owner(BA000)
	 */
	OWNER_NOT_FOUND(NOT_FOUND, "BA001", "사업자를 찾을 수 없습니다."),
	DUPLICATE_OWNER_BAKERY(CONFLICT, "BA002", "해당 사업자에 이미 빵집이 존재합니다."),
	/**
	 * Address(BB000)
	 */

	/**
	 * Region(BC000)
	 */
	REGION_NOT_FOUND(NOT_FOUND, "BC001", "지역을 찾을 수 없습니다."),

	/**
	 * Bakery(BD000)
	 */
	BAKERY_NOT_FOUND(NOT_FOUND, "BD001", "빵집을 찾을 수 없습니다."),
	BAKERY_INACTIVE(NOT_FOUND, "BD002", "삭제된 빵집입니다."),
	OWNER_MISMATCH(NOT_FOUND, "BD003", "빵집 주인이 아닙니다.");
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
