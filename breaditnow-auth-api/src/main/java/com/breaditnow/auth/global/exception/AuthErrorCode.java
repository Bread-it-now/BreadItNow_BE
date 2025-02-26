package com.breaditnow.auth.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
	/**
	 * 소셜 로그인(BA000)
	 */
	UNSUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "BA001", "지원하지 않는 소셜입니다."),

	/**
	 * 기타(BC000)
	 */
	SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BC001", "AuthToken을 JSON으로 직렬화하는데 실패했습니다."),
	DESERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BC002", "JSON을 AuthToken으로 역직렬화하는데 실패했습니다.");

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

