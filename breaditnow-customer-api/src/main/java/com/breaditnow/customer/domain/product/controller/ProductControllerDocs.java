package com.breaditnow.customer.domain.product.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.product.controller.res.HotProductPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 API", description = "인기 상품 목록을 조회하는 API입니다.")
public interface ProductControllerDocs {

	@Operation(summary = "핫 상품 목록 조회", description = "인기 상품 목록을 페이지네이션 형태로 조회합니다.")
	@Parameters({
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
		@Parameter(name = "size", description = "한 페이지당 노출할 상품 개수", example = "10", in = QUERY),
		@Parameter(name = "sort", description = "정렬 기준", example = "reservation", in = QUERY)
	})
	ApiSuccessResponse<HotProductPageResponse> searchHotProducts(Long customerId, int page, int size, String sort);
}
