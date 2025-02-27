package com.breaditnow.owner.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum OwnerErrorCode implements ErrorCode {
	/**
	 * DA000 File
	 */
	FILE_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DA001", "파일 생성에 실패했습니다."),
	FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DA002", "S3 파일 업로드에 실패했습니다."),

	/**
	 * DB000 Bakery
	 */
	INVALID_OWNER(HttpStatus.UNAUTHORIZED, "DB001", "사업자 아이디가 일치하지 않습니다."),

	/**
	 * DC000
	 */

	/**
	 * DY000 인증
	 */
	AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "DY001", "인증 정보가 필수입니다.");

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

