package com.breaditnow.customer.domain.product.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.product.controller.res.HotProductPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 API", description = "고객이 상품 관련 정보를 조회하는 API입니다.")
public interface ProductControllerDocs {

	@Operation(
		summary = "핫 상품 목록 조회",
		description = "고객 ID, 페이지, 사이즈, 정렬 기준을 기반으로 핫 상품 목록을 조회합니다. 이 API는 고객의 요청에 따라 인기 상품 데이터를 페이징하여 반환합니다."
	)
	ApiSuccessResponse<HotProductPageResponse> searchHotProducts(Long customerId, int page, int size, String sort);
}
