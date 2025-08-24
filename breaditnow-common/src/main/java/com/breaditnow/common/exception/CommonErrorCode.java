package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	/**
	 * 공통(AA000)
	 */
	INVALID_PARAMETER(BAD_REQUEST, "AA001", "잘못된 파라미터가 포함되었습니다."),
	ARGUMENT_TYPE_MISMATCH(BAD_REQUEST, "AA002", "파라미터의 타입이 잘못되었습니다"),
	UNDEFINED_ERROR(INTERNAL_SERVER_ERROR, "AA003", "정의되지 않은 에러입니다."),
	RABBITMQ_COMMUNICATION_ERROR(INTERNAL_SERVER_ERROR, "AA004", "RabbitMQ 통신 오류가 발생했습니다."),

	/**
	 * 외부 API(AB000)
	 */
	EXTERNAL_API_NOT_FOUND(HttpStatus.NOT_FOUND, "AB001", "외부 API를 찾을 수 없습니다."),
	EXTERNAL_API_METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "AB002", "허용되지 않은 HTTP 메서드입니다."),
	EXTERNAL_API_UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "AB003", "지원되지 않는 미디어 타입입니다."),
	EXTERNAL_API_BAD_REQUEST(INTERNAL_SERVER_ERROR, "AB004", "잘못된 요청입니다."),
	EXTERNAL_API_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "AB005", "서버 내부 오류가 발생했습니다."),

	/**
	 * Webhook(AC000)
	 */
	CREATE_DISCORD_APPEND_MESSAGE_FAILURE(INTERNAL_SERVER_ERROR, "AC001", "디스코드 메시지 생성을 실패했습니다."),
	DISCORD_SEND_MESSAGE_FAILURE(INTERNAL_SERVER_ERROR, "AC001", "디스코드 메시지 전송을 실패했습니다."),
	MISSING_DISCORD_CONTENT_OR_EMBEDS(BAD_REQUEST, "AC003", "디스코드 메시지 내용 또는 Embed가 누락되었습니다."),

	/**
	 * Json(AD000)
	 */
	JSON_PARSER_ERROR(INTERNAL_SERVER_ERROR, "AD001", "Json Parser 오류"),
	JSON_SERIALIZATION_ERROR(INTERNAL_SERVER_ERROR, "AD002", "JSON으로 직렬화하는데 실패했습니다."),
	JSON_DESERIALIZATION_ERROR(INTERNAL_SERVER_ERROR, "AD003", "JSON을 역직렬화하는데 실패했습니다."),

	/**
	 * Domain(AE000)
	 */
	INVALID_AMOUNT(BAD_REQUEST, "AE001", "금액은 0보다 작을 수 없습니다."),
	AMOUNT_REQUIRED(BAD_REQUEST, "AE002", "금액은 필수 값입니다."),
	SORT_CONDITION_NOT_FOUND(BAD_REQUEST, "AE003", "정렬 조건을 찾을 수 없습니다."),
	INVALID_PAGE_NUMBER(BAD_REQUEST, "AE004", "페이지 번호는 0 이상이어야 합니다."),
	INVALID_PAGE_SIZE(BAD_REQUEST, "AE005", "페이지 크기는 1 이상 100 이하이어야 합니다."),
	INVALID_TIME_FORMAT(BAD_REQUEST, "AE006", "잘못된 시간 형식입니다. 올바른 형식은 HH:mm 입니다."),
	COORDINATES_REQUIRED(BAD_REQUEST, "AE007", "좌표는 필수 값입니다."),
	INVALID_LATITUDE_RANGE(BAD_REQUEST, "AE008", "위도는 -90.0에서 90.0 사이여야 합니다."),
	INVALID_LONGITUDE_RANGE(BAD_REQUEST, "AE009", "경도는 -180.0에서 180.0 사이여야 합니다."),
	INVALID_PERIOD_VALUE(BAD_REQUEST, "AE010", "잘못된 기간 값입니다. DAILY, WEEKLY, MONTHLY 중 하나여야 합니다."),

	/**
	 * AUTH(AF000)
	 */
	MISSING_AUTHORIZATION_HEADER(BAD_REQUEST, "AE011", "Authorization 헤더가 누락되었습니다.");

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
