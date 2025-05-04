package com.breaditnow.owner.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum OwnerErrorCode implements ErrorCode {
	/**
	 * DA000 Owner
	 */
	PASSWORD_SAME_AS_CURRENT(HttpStatus.BAD_REQUEST, "DA001", "현재 비밀번호와 일치합니다. 다른 비밀번호를 입력해주세요."),

	/**
	 * DB000 Bakery
	 */
	COORDINATE_NOT_FOUND(HttpStatus.NOT_FOUND, "DB001", "주어진 주소에서 위도, 경도를 찾을 수 없습니다."),

	/**
	 * DY000 인증
	 */
	AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "DY001", "Owner 인증 정보가 필수입니다.");

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

