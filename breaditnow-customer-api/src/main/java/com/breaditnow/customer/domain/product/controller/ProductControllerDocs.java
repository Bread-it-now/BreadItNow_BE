package com.breaditnow.customer.domain.product.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.product.controller.res.HotProductPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 API")
public interface ProductControllerDocs {
	@Operation(summary = "고객 ID, 페이지, 사이즈, 정렬 기준을 기반으로 핫 상품 목록을 조회합니다.")
	ApiSuccessResponse<HotProductPageResponse> searchHotProducts(Long customerId, int page, int size, String sort);
}
