package com.breaditnow.customer.domain.alert.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.alert.controller.res.CustomerProductAlertPageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 알림")
public interface CustomerProductAlertControllerDocs {

	@Operation(summary = "고객 ID와 상품 ID를 기반으로 상품 알림을 등록합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND, ALERT_ALREADY_EXISTS})
	ApiSuccessResponse<Map<String, Long>> registerProductAlert(Long customerId, Long productId);

	@Operation(summary = "고객 ID와 상품 ID를 기반으로 상품 알림을 삭제합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND})
	ApiSuccessResponse<Void> deleteProductAlert(Long customerId, Long productId);

	@Operation(summary = "고객 ID와 상품 ID를 기반으로 상품 알림의 활성/비활성 상태를 전환합니다.")
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND, ALERT_NOT_FOUND})
	ApiSuccessResponse<Map<String, Boolean>> toggleProductAlert(Long customerId, Long productId);

	@Operation(summary = "고객 ID와 페이지 정보를 기반으로 상품 알림 목록을 조회합니다.")
	ApiSuccessResponse<CustomerProductAlertPageResponse> getProductAlerts(Long customerId, int page, int size);
}
