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
	TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "BB001", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "BB002", "만료된 토큰입니다."),
	TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "BB003", "지원되지 않는 토큰입니다."),
	TOKEN_WRONG(HttpStatus.UNAUTHORIZED, "BB004", "잘못된 토큰 서명입니다."),
	TOKEN_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "BB005", "토큰이 일치하지 않습니다."),
	REFRESH_TOKEN_NOT_EXIST_IN_COOKIE(HttpStatus.UNAUTHORIZED, "AB006", "쿠키에 Refresh 토큰이 없습니다. 재로그인해주세요."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AB007", "만료된 Refresh 토큰 입니다. 재로그인해주세요."),

	/**
	 * 기타(BC000)
	 */
	SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BC001", "AuthToken을 JSON으로 직렬화하는데 실패했습니다."),
	DESERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BC002", "JSON을 AuthToken으로 역직렬화하는데 실패했습니다."),

	/**
	 * Role 관련(BD000)
	 */
	ROLE_INVALID(HttpStatus.BAD_REQUEST, "AD001", "잘못된 역할 값입니다. 유효한 값은 CUSTOMER 또는 OWNER 입니다."),

	/**
	 * 직접 로그인 관련(BE000)
	 */
	EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "BE001", "이메일이 존재하지 않습니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "BE002", "이미 가입된 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "BE003", "비밀번호가 일치하지 않습니다."),
	LOGIN_FAILED(HttpStatus.BAD_REQUEST, "BE004", "로그인에 실패하였습니다.");

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

