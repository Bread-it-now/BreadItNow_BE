package com.breaditnow.customer.domain.alert.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.alert.controller.res.CustomerProductAlertPageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 알림", description = "고객이 상품 알림을 등록, 삭제, 전환하고 조회하는 API")
public interface CustomerProductAlertControllerDocs {

	@Operation(
		summary = "상품 알림 등록",
		description = "고객 ID와 상품 ID를 기반으로 해당 상품에 대한 알림을 등록합니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND, ALERT_ALREADY_EXISTS})
	ApiSuccessResponse<Map<String, Long>> registerProductAlert(Long customerId, Long productId);

	@Operation(
		summary = "상품 알림 삭제",
		description = "고객 ID와 상품 ID를 기반으로 해당 상품에 등록된 알림을 삭제합니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND})
	ApiSuccessResponse<Void> deleteProductAlert(Long customerId, Long productId);

	@Operation(
		summary = "상품 알림 상태 전환",
		description = "고객 ID와 상품 ID를 기반으로 해당 상품 알림의 활성/비활성 상태를 전환합니다."
	)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND, ALERT_NOT_FOUND})
	ApiSuccessResponse<Map<String, Boolean>> toggleProductAlert(Long customerId, Long productId);

	@Operation(
		summary = "상품 알림 목록 조회",
		description = "고객 ID와 페이지 정보를 기반으로 페이징된 상품 알림 목록을 조회합니다."
	)
	ApiSuccessResponse<CustomerProductAlertPageResponse> getProductAlerts(Long customerId, int page, int size);
}
