package com.breaditnow.gateway.exception;

import org.springframework.http.HttpStatus;

public enum GatewayErrorCode {
	/**
	 * 토큰(ZA000)
	 */
	TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AB001", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AB002", "만료된 토큰입니다."),
	TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "AB003", "지원되지 않는 토큰입니다."),
	TOKEN_WRONG(HttpStatus.UNAUTHORIZED, "AB004", "잘못된 토큰 서명입니다."),
	TOKEN_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "AB005", "토큰이 일치하지 않습니다."),

	/**
	 * Authorization 헤더 관련 에러(ZB000)
	 */
	MISSING_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED, "ZB001", "Authorization 헤더가 누락되었습니다."),
	INVALID_AUTHORIZATION_HEADER_FORMAT(HttpStatus.UNAUTHORIZED, "ZB002", "Authorization 헤더 형식이 올바르지 않습니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "ZB003", "권한이 없는 접근입니다."),
	INVALID_ROLE(HttpStatus.FORBIDDEN, "ZB004", "유효하지 않은 권한입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	GatewayErrorCode(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}

