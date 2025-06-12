package com.breaditnow.customer.common.exception;

import com.breaditnow.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

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
	PRODUCT_NOT_ACTIVE(BAD_REQUEST, "CD001", "비활성화된 상품입니다."),
	INVALID_AMOUNT(BAD_REQUEST, "CD002", "금액은 0보다 작을 수 없습니다."),
	AMOUNT_REQUIRED(BAD_REQUEST, "CD003", "금액은 필수 값입니다."),
	INVALID_STOCK(BAD_REQUEST, "CD004", "재고는 0보다 작을 수 없습니다."),
	PRODUCT_ALREADY_HIDDEN(CONFLICT, "CD005", "상품은 이미 숨김 상태입니다."),
	PRODUCT_ALREADY_UNHIDDEN(CONFLICT, "CD006", "상품은 이미 숨김 해제 상태입니다."),
	INVALID_PERIOD_VALUE(BAD_REQUEST, "CD007", "기간은 DAILY, WEEKLY, MONTHLY 중에서 선택해야 합니다."),
	PERIOD_REQUIRED_FOR_RESERVATION_SORT(BAD_REQUEST, "CD008", "예약순 정렬 시 기간 설정은 필수입니다."),
	INVALID_HOT_SORT_TYPE(BAD_REQUEST, "CD009", "핫 상품은 RESERVATION, FAVORITE 중에서 선택해야 합니다."),
	INVALID_PRODUCT_CATEGORY(BAD_REQUEST, "CD010", "상품 카테고리는 BREAD, OTHER 중에서 선택해야 합니다."),
	ONLY_BREAD_CAN_BE_FAVORITED(BAD_REQUEST, "CD011", "빵 상품만 즐겨찾기할 수 있습니다."),

	/**
	 * CE000 FAVORITE
	 */
	ALREADY_FAVORITED(CONFLICT, "CE001", "이미 즐겨찾기된 상품입니다."),
	NOT_FAVORITED(NOT_FOUND, "CE002", "즐겨찾기되지 않은 상품입니다."),

	/**
	 * CZ000 이외
	 */
	INVALID_PAGE_NUMBER(BAD_REQUEST, "CZ001", "페이지 번호는 0 이상이어야 합니다."),
	INVALID_PAGE_SIZE(BAD_REQUEST, "CZ002", "페이지 크기는 1에서 100 사이여야 합니다."),
	COORDINATES_REQUIRED(BAD_REQUEST, "CZ003", "위도와 경도는 null일 수 없습니다."),
	INVALID_LATITUDE_RANGE(BAD_REQUEST, "CZ004", "위도는 -90과 90 사이여야 합니다."),
	INVALID_LONGITUDE_RANGE(BAD_REQUEST, "CZ005", "경도는 -180과 180 사이여야 합니다.");

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

