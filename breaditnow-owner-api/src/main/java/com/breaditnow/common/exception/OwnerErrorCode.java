package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum OwnerErrorCode implements ErrorCode {
	/**
	 * DA000 Owner
	 */
	PASSWORD_SAME_AS_CURRENT(BAD_REQUEST, "DA001", "현재 비밀번호와 일치합니다. 다른 비밀번호를 입력해주세요."),
	DUPLICATE_NICKNAME(CONFLICT, "DA002", "닉네임이 중복됩니다. 다른 닉네임을 입력해주세요"),

	/**
	 * DB000 Bakery
	 */
	COORDINATE_NOT_FOUND(NOT_FOUND, "DB001", "주어진 주소에서 위도, 경도를 찾을 수 없습니다."),
	REGION_CODE_REQUIRED(BAD_REQUEST, "DB002", "법정 행정동 코드는 필수입니다."),
	FULL_ADDRESS_REQUIRED(BAD_REQUEST, "DB003", "전체 주소는 필수입니다."),
	GEOLOCATION_NOT_FOUND(NOT_FOUND, "DB004", "해당 주소의 위도, 경도를 찾을 수 없습니다."),
	PHONE_NUMBER_REQUIRED(BAD_REQUEST, "DB005", "전화번호는 필수입니다."),
	INVALID_PHONE_NUMBER_FORMAT(BAD_REQUEST, "DB006", "전화번호 형식이 올바르지 않습니다."),
	UNAUTHORIZED_BAKERY_ACCESS(FORBIDDEN, "DB007", "이 빵집에 대한 접근 권한이 없습니다."),
	BAKERY_INACTIVE(FORBIDDEN, "DB008", "비활성화된 빵집입니다. 활성화 후 다시 시도해주세요."),
	BAKERY_NOT_FOUND(NOT_FOUND, "DB009", "해당 빵집을 찾을 수 없습니다."),

	/**
	 * DC000 Image
	 */
	IMAGE_NOT_FOUND(NOT_FOUND, "DC001", "이미지를 찾을 수 없습니다."),
	IMAGE_REQUIRED(BAD_REQUEST, "DC002", "이미지는 필수입니다."),
	IMAGE_UPLOAD_FAILED(INTERNAL_SERVER_ERROR, "DC003", "이미지 업로드에 실패했습니다."),
	IMAGE_UPLOAD_FAILED_WITH_ERROR(INTERNAL_SERVER_ERROR, "DC004", "이미지 업로드 중 오류가 발생했습니다."),

	/**
	 * DD000 Product
	 */
	INVALID_AMOUNT(BAD_REQUEST, "DD002", "금액은 0보다 작을 수 없습니다."),
	AMOUNT_REQUIRED(BAD_REQUEST, "DD003", "금액은 필수 값입니다."),
	INVALID_STOCK(BAD_REQUEST, "DD004", "재고는 0보다 작을 수 없습니다."),
	INVALID_PRODUCT_TYPE(BAD_REQUEST, "DD007", "상품 카테고리는 BREAD, OTHER 중에서 선택해야 합니다."),
	PRODUCT_CATEGORY_TYPE_REQUIRED(BAD_REQUEST, "DD008", "상품 카테고리는 필수입니다."),
	BREAD_CATEGORY_REQUIRED(BAD_REQUEST, "DD009", "BREAD 상품 유형에는 빵 카테고리 ID가 필수입니다."),
	BREAD_CATEGORY_MUST_BE_EMPTY(BAD_REQUEST, "DD010", "OTHER 상품 유형에는 빵 카테고리 ID가 없어야 합니다."),
	INVALID_PRODUCT_STATUS(BAD_REQUEST, "DD011", "유효하지 않은 상품 상태입니다. FOR_SALE, HIDDEN, SOLD_OUT 중에서 선택해야 합니다."),
	PRODUCT_ALREADY_IN_SAME_STATUS(CONFLICT, "DD012", "상품이 이미 요청된 상태와 동일합니다."),
	DUPLICATE_PRODUCT_ID_IN_REQUEST(BAD_REQUEST, "DD013", "상품 순서 변경 요청에 중복된 상품 ID가 포함될 수 없습니다."),
	DUPLICATE_DISPLAY_ORDER(BAD_REQUEST, "DD014", "상품 진열 순서에 중복된 값이 포함될 수 없습니다."),
	DISPLAY_ORDER_SET_MISMATCH(BAD_REQUEST, "DD015", "변경 대상 상품들의 기존 순서 값과 일치하지 않는 순서 값이 포함되어 있습니다."),
	PRODUCT_NOT_FOUND(NOT_FOUND, "DD016", "해당 상품을 찾을 수 없습니다."),
	/**
	 * DY000 인증
	 */
	AUTHENTICATION_REQUIRED(UNAUTHORIZED, "DY001", "Owner 인증 정보가 필수입니다."),


	/**
	 * DZ000 이외
	 */
	INVALID_TIME_FORMAT(BAD_REQUEST, "CC008", "시간 형식이 잘못되었습니다. 'HH:mm' 형식이어야 합니다."), ;


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

