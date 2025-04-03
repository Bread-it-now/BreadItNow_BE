package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	/**
	 * 공통(AA000)
	 */
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "AA001", "잘못된 파라미터가 포함되었습니다."),
	ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "AA002", "파라미터의 타입이 잘못되었습니다"),
	UNDEFINED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AA003", "정의되지 않은 에러입니다."),

	/**
	 * 외부 API(AB000)
	 */
	EXTERNAL_API_NOT_FOUND(HttpStatus.NOT_FOUND, "AB001", "외부 API를 찾을 수 없습니다."),
	EXTERNAL_API_METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "AB002", "허용되지 않은 HTTP 메서드입니다."),
	EXTERNAL_API_UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "AB003", "지원되지 않는 미디어 타입입니다."),
	EXTERNAL_API_BAD_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "AB004", "잘못된 요청입니다."),
	EXTERNAL_API_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AB005", "서버 내부 오류가 발생했습니다."),

	/**
	 * Webhook(AC000)
	 */
	JSON_PARSER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Y002", "Json Parser 오류"),
	CREATE_DISCORD_APPEND_MESSAGE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "Y001", "디스코드 메시지 생성을 실패했습니다."),
	DISCORD_SEND_MESSAGE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "Y001", "디스코드 메시지 전송을 실패했습니다."),
	MISSING_DISCORD_CONTENT_OR_EMBEDS(HttpStatus.BAD_REQUEST, "Y003", "디스코드 메시지 내용 또는 Embed가 누락되었습니다.");

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
