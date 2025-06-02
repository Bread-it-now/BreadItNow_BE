package com.breaditnow.customer.common.presentation.swagger.docs;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.alert.application.response.ProductAlertPageResponse;
import com.breaditnow.customer.common.presentation.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 알림 API", description = "특정 상품에 대한 알림(재입고, 새로운 소식 등)을 등록/관리하기 위한 API입니다.")
public interface CustomerProductAlertControllerDocs {

	@Operation(summary = "상품 알림 등록", description = "특정 상품(product_id)에 대한 알림을 활성화합니다.")
	@Parameter(name = "productId", description = "알림 등록할 상품 ID", example = "1001", required = true)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND, ALERT_ALREADY_EXISTS})
	ApiSuccessResponse<Map<String, Long>> registerProductAlert(Long customerId, Long productId);

	@Operation(summary = "상품 알림 삭제", description = "특정 상품(product_id)에 대한 알림을 삭제합니다.")
	@Parameter(name = "productId", description = "알림 삭제할 상품 ID", example = "1001", required = true)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND})
	ApiSuccessResponse<Void> deleteProductAlert(Long customerId, Long productId);

	@Operation(summary = "상품 알림 상태 전환", description = "특정 상품 알림의 활성화 상태를 토글합니다 (활성, 비활성).")
	@Parameter(name = "productId", description = "알림 상태를 전환할 상품 ID", example = "1001", required = true)
	@DomainErrorCodeExamples({CUSTOMER_NOT_FOUND, PRODUCT_NOT_FOUND, ALERT_NOT_FOUND})
	ApiSuccessResponse<Map<String, Boolean>> toggleProductAlert(Long customerId, Long productId);

	@Operation(summary = "상품 알림 목록 조회", description = "고객의 등록된 상품 알림 목록을 페이징하여 조회합니다.")
	@Parameters({
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
		@Parameter(name = "size", description = "한 페이지당 최대 데이터 개수", example = "10", in = QUERY)
	})
	ApiSuccessResponse<ProductAlertPageResponse> getProductAlerts(Long customerId, int page, int size);
}
