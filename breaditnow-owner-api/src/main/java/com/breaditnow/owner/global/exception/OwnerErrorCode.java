package com.breaditnow.owner.global.exception;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

import static org.springframework.http.HttpStatus.*;

public enum OwnerErrorCode implements ErrorCode {
	/**
	 * DA000 Owner
	 */
	PASSWORD_SAME_AS_CURRENT(BAD_REQUEST, "DA001", "현재 비밀번호와 일치합니다. 다른 비밀번호를 입력해주세요."),

	/**
	 * DB000 Bakery
	 */
	COORDINATE_NOT_FOUND(NOT_FOUND, "DB001", "주어진 주소에서 위도, 경도를 찾을 수 없습니다."),
	REGION_CODE_REQUIRED(BAD_REQUEST, "DB002", "법정 행정동 코드는 필수입니다."),
	FULL_ADDRESS_REQUIRED(BAD_REQUEST, "DB003", "전체 주소는 필수입니다."),
	GEOLOCATION_NOT_FOUND(NOT_FOUND, "DB004", "해당 주소의 위도, 경도를 찾을 수 없습니다."),
	PHONE_NUMBER_REQUIRED(BAD_REQUEST, "DB005", "전화번호는 필수입니다."),
	INVALID_PHONE_NUMBER_FORMAT(BAD_REQUEST, "DB006", "전화번호 형식이 올바르지 않습니다."),
	UNAUTHORIZED_BAKERY_ACCESS(FORBIDDEN, "DB006", "이 빵집에 대한 삭제 권한이 없습니다."),

	/**
	 * DC000 Image
	 */
	IMAGE_NOT_FOUND(NOT_FOUND, "DC001", "이미지를 찾을 수 없습니다."),
	IMAGE_REQUIRED(BAD_REQUEST, "DC002", "이미지는 필수입니다."),
	IMAGE_UPLOAD_FAILED(INTERNAL_SERVER_ERROR, "DC003", "이미지 업로드에 실패했습니다."),
	IMAGE_UPLOAD_FAILED_WITH_ERROR(INTERNAL_SERVER_ERROR, "DC004", "이미지 업로드 중 오류가 발생했습니다."),


	/**
	 * DY000 인증
	 */
	AUTHENTICATION_REQUIRED(UNAUTHORIZED, "DY001", "Owner 인증 정보가 필수입니다.");


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

