package com.breaditnow.domain.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.breaditnow.common.exception.ErrorCode;

public enum DomainErrorCode implements ErrorCode {
	/**
	 * Owner(BA000)
	 */
	OWNER_NOT_FOUND(NOT_FOUND, "BA001", "사업자를 찾을 수 없습니다."),
	DUPLICATE_OWNER_BAKERY(CONFLICT, "BA002", "해당 사업자에 이미 빵집이 존재합니다."),
	/**
	 * Address(BB000)
	 */

	/**
	 * Region(BC000)
	 */
	REGION_NOT_FOUND(NOT_FOUND, "BC001", "지역을 찾을 수 없습니다."),

	/**
	 * Bakery(BD000)
	 */
	BAKERY_NOT_FOUND(NOT_FOUND, "BD001", "빵집을 찾을 수 없습니다."),
	BAKERY_INACTIVE(NOT_FOUND, "BD002", "삭제된 빵집입니다."),
	OWNER_MISMATCH(NOT_FOUND, "BD003", "빵집 주인이 아닙니다."),
	BREAD_NOT_FOUND(NOT_FOUND, "BD004", "빵을 찾을 수 없습니다."),

	/**
	 * Customer(BE000)
	 */
	CUSTOMER_NOT_FOUND(NOT_FOUND, "BE001", "고객을 찾을 수 없습니다"),

	/**
	 * Favorite(BF000)
	 */
	BAKERY_FAVORITE_NOT_FOUND(NOT_FOUND, "BF001", "좋아요한 빵집이 아닙니다."),
	BAKERY_FAVORITE_IS_DUPLICATED(CONFLICT, "BF002", "이미 좋아요한 빵집입니다."),
	BAKERY_SORT_CONDITION_NOT_FOUND(BAD_REQUEST, "BF004", "빵집에서 존재하지 않는 정렬 조건입니다."),

	BREAD_FAVORITE_NOT_FOUND(NOT_FOUND, "BF003", "좋아요한 빵이 아닙니다."),
	BREAD_SORT_CONDITION_NOT_FOUND(BAD_REQUEST, "BF004", "빵에서 존재하지 않는 정렬 조건입니다."),
	CURRENT_LOCATION_NOT_SET(BAD_REQUEST, "BF005", "현재 위치 정보가 설정되지 않았습니다."),

	/**
	 * Product(BG000)
	 */
	INVALID_PRODUCT_TYPE(BAD_REQUEST, "BG001", "잘못된 빵집 Type입니다."),
	PRODUCT_NOT_FOUND(NOT_FOUND, "BG002", "메뉴를 찾을 수 없습니다."),
	PRODUCT_INACTIVE(NOT_FOUND, "BG003", "삭제된 메뉴입니다."),
	BAKERY_MISMATCH(NOT_FOUND, "BG004", "해당 메뉴의 빵집이 아닙니다."),

	/**
	 * BreadCategory(BH000)
	 */
	BREAD_CATEGORY_NOT_FOUND(NOT_FOUND, "BH001", "빵 카레고리를 찾을 수 없습니다."),

	/**
	 * Reservation(BI000)
	 */
	RESERVATION_NOT_FOUND(NOT_FOUND, "BI001", "예약을 찾을 수 없습니다."),
	RESERVATION_ALREADY_CANCELLED(BAD_REQUEST, "BI002", "이미 취소된 예약입니다."),

	/**
	 * Alert(BJ000)
	 */
	ALERT_NOT_FOUND(NOT_FOUND, "BJ001", "등록된 알람이 없습니다."),
	ALERT_ALREADY_INACTIVE(BAD_REQUEST, "BJ002", "이미 비활성화된 알림입니다."),
	ALERT_ALREADY_EXISTS(CONFLICT, "BI003", "이미 등록된 알림입니다."),

	/**
	 * Notification(BK000)
	 */
	NOTIFICATION_NOT_FOUND(NOT_FOUND, "BK001", "알림을 찾을 수 없습니다."),
	INVALID_NOTIFICATION_TYPE(BAD_REQUEST, "BK002", "잘못된 알림 타입입니다."),
	INVALID_RESERVATION_STATUS_TYPE(BAD_REQUEST, "BK002", "잘못된 예약 상태 타입입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	DomainErrorCode(HttpStatus httpStatus, String code, String message) {
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
