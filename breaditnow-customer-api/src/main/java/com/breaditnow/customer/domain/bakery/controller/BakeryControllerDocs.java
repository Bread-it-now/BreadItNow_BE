package com.breaditnow.customer.domain.bakery.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 빵집 API")
public interface BakeryControllerDocs {
	@Operation(summary = "고객 ID와 빵집 ID를 기반으로 빵집 상세 정보를 조회합니다.")
	@DomainErrorCodeExamples({BAKERY_INACTIVE, BAKERY_NOT_FOUND})
	ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(Long customerId, Long bakeryId);

	@Operation(summary = "고객 ID, 페이지, 정렬, 위치 정보를 기반으로 인기 빵집 목록을 조회합니다.")
	@DomainErrorCodeExamples({BAKERY_SORT_CONDITION_NOT_FOUND})
	ApiSuccessResponse<HotBakeryPageResponse> searchHotBakeries(Long customerId, int page, int size, String sort,
		GeoPointRequest geoPointRequest);
}
