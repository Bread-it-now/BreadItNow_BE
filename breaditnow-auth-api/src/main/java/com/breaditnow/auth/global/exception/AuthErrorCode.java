package com.breaditnow.auth.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
	/**
	 * 소셜 로그인(BA000)
	 */
	UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "BA001", "지원하지 않는 소셜입니다."),

	/**
	 * 토큰(BB000)
	 */
	OAUTH2_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "BB002", "소셜 Access Token이 만료되었습니다. 재로그인해주세요");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	AuthErrorCode(HttpStatus httpStatus, String code, String message) {
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

