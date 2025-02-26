package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "AA01", "잘못된 파라미터가 포함되었습니다."),
	UNDEFINED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AA02", "정의되지 않은 에러입니다."),

	/**
	 * 토큰(AB000)
	 */
	OAUTH2_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AB001", "소셜 Access Token이 만료되었습니다. 재로그인해주세요."),
	TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AB002", "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AB003", "만료된 토큰입니다."),
	TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "AB004", "지원되지 않는 토큰입니다."),
	TOKEN_WRONG(HttpStatus.UNAUTHORIZED, "AB005", "잘못된 토큰 서명입니다."),
	REFRESH_TOKEN_NOT_EXIST_IN_COOKIE(HttpStatus.UNAUTHORIZED, "AB006", "쿠키에 Refresh 토큰이 없습니다. 재로그인해주세요."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AB007", "만료된 Refresh 토큰 입니다. 재로그인해주세요."),
	TOKEN_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "AB008", "토큰이 일치하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

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
