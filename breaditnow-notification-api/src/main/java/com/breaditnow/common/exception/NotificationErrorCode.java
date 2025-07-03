package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum NotificationErrorCode implements ErrorCode {
	/**
	 * FA000 Notification
	 */
	BAKERY_NOT_FOUND(NOT_FOUND, "EA001", "해당 빵집을 찾을 수 없습니다."),
	FORBIDDEN_ACCESS(FORBIDDEN, "EA002", "접근 권한이 없습니다."),
	UNAUTHORIZED_ACCESS(UNAUTHORIZED, "EA003", "인증되지 않은 사용자입니다."),
	ALREADY_READ_NOTIFICATION(CONFLICT, "EA004", "이미 읽은 알림입니다."),
	NOTIFICATION_NOT_FOUND(NOT_FOUND, "EA005", "알림을 찾을 수 없습니다."),
	ALREADY_DELETED_NOTIFICATION(CONFLICT, "EA006", "이미 삭제된 알림입니다."),;



	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	NotificationErrorCode(HttpStatus httpStatus, String code, String message) {
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

