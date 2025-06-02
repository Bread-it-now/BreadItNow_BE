package com.breaditnow.customer.common.exception;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum CustomerErrorCode implements ErrorCode {
	/**
	 * CY000 인증
	 */
	AUTHENTICATION_REQUIRED(UNAUTHORIZED, "CY001", "Customer 인증 정보가 필수입니다."),

	/**
	 * CA000 고객 정보
	 */
	INVALID_NICKNAME(BAD_REQUEST, "CA001", "닉네임은 null이거나 빈 문자열일 수 없습니다."),
	INVALID_PROFILE_IMAGE_URL(BAD_REQUEST, "CA002", "프로필 이미지 URL은 null이거나 빈 문자열일 수 없습니다."),
	INVALID_PASSWORD(BAD_REQUEST, "CA003", "비밀번호는 null이거나 빈 문자열일 수 없습니다."),
	DUPLICATE_PRODUCT_CATEGORY_PREFERENCE(BAD_REQUEST, "CY004", "이미 선호 카테고리로 등록된 항목입니다."),
	INVALID_BREAD_CATEGORY_IDS(BAD_REQUEST, "CY005", "유효하지 않은 빵 카테고리 ID입니다."),
	INVALID_REGION_PREFERENCE(BAD_REQUEST, "CA006", "관심 지역은 하나 이상 선택해야 합니다."),
	ALREADY_INITIALIZED(BAD_REQUEST, "CA007", "이미 회원가입 초기화가 완료된 회원입니다"),


	/**
	 * CB000 FILE
	 */
	FILE_CREATION_FAILED(INTERNAL_SERVER_ERROR, "CB001", "파일 생성에 실패했습니다."),
	FILE_UPLOAD_FAILED(INTERNAL_SERVER_ERROR, "CB002", "S3 파일 업로드에 실패했습니다."),

	/**
	 * CC000 Alert
	 */
	INVALID_TIME_RANGE(BAD_REQUEST, "CC001", "시작 시간이 종료 시간보다 이후입니다."),
	INVALID_START_TIME(BAD_REQUEST, "CC002", "시작 시간은 null일 수 없습니다."),
	INVALID_END_TIME(BAD_REQUEST, "CC003", "종료 시간은 null일 수 없습니다."),
	EMPTY_DND_DAYS(BAD_REQUEST, "CC004", "방해금지 모드의 요일은 비어있을 수 없습니다."),
	INVALID_DND_DAY_VALUE(BAD_REQUEST, "CC005", "방해금지 모드의 요일은 MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY 중에서 선택해야 합니다."),
	ALREADY_ACTIVE(BAD_REQUEST, "CC006", "이미 활성화된 방해금지 모드입니다."),
	ALREADY_INACTIVE(BAD_REQUEST, "CC007", "이미 비활성화된 방해금지 모드입니다."),
	INVALID_TIME_FORMAT(BAD_REQUEST, "CC008", "시간 형식이 잘못되었습니다. 'HH:mm' 형식이어야 합니다."),
	ALERT_ALREADY_ACTIVE(CONFLICT, "CC010", "이미 등록된 알림입니다."),

	/**
	 * CD000 Product
	 */
	PRODUCT_NOT_ACTIVE(BAD_REQUEST, "CD001", "비활성화된 상품입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	CustomerErrorCode(HttpStatus httpStatus, String code, String message) {
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

