package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public enum AuthErrorCode implements ErrorCode {
	/**
	 * 로그인(BA000)
	 */
	USER_NOT_FOUND(BAD_REQUEST, "BA001", "존재하지 않는 사용자입니다."),
	EMAIL_NOT_FOUND(BAD_REQUEST, "BA002", "이메일이 존재하지 않습니다."),
	EMAIL_ALREADY_EXISTS(CONFLICT, "BA003", "이미 가입된 이메일입니다."),
	AUTHENTICATION_FAILED(UNAUTHORIZED, "BA005", "인증에 실패했습니다."),
	INVALID_CREDENTIALS(UNAUTHORIZED, "BA006", "아이디 또는 비밀번호가 일치하지 않습니다."),
	/**
	 * 토큰(BB000)
	 */
	TOKEN_INVALID(UNAUTHORIZED, "BB001", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(UNAUTHORIZED, "BB002", "만료된 토큰입니다."),
	TOKEN_UNSUPPORTED(UNAUTHORIZED, "BB003", "지원되지 않는 토큰입니다."),
	TOKEN_WRONG(UNAUTHORIZED, "BB004", "잘못된 토큰 서명입니다."),
	TOKEN_NOT_MATCHED(UNAUTHORIZED, "BB005", "토큰이 일치하지 않습니다."),
	REFRESH_TOKEN_NOT_EXIST_IN_COOKIE(UNAUTHORIZED, "BB006", "쿠키에 Refresh 토큰이 없습니다. 재로그인해주세요."),
	REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "BB007", "만료된 Refresh 토큰 입니다. 재로그인해주세요."),

	/**
	 * 기타(BC000)
	 */
	SERIALIZATION_ERROR(INTERNAL_SERVER_ERROR, "BC001", "AuthToken을 JSON으로 직렬화하는데 실패했습니다."),
	DESERIALIZATION_ERROR(INTERNAL_SERVER_ERROR, "BC002", "JSON을 AuthToken으로 역직렬화하는데 실패했습니다."),

	/**
	 * Role 관련(BD000)
	 */
	ROLE_INVALID(BAD_REQUEST, "BD001", "잘못된 역할 값입니다. 유효한 값은 CUSTOMER 또는 OWNER 입니다."),

	/**
	 * 직접 로그인 관련(BE000)
	 */


	/**
	 * 이메일 인증 관련(BF000)
	 */
	CODE_EXPIRED  (BAD_REQUEST, "BF001", "인증 코드가 만료되었습니다."),
	CODE_MISMATCH (BAD_REQUEST, "BF002", "인증 코드가 일치하지 않습니다."),;

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

