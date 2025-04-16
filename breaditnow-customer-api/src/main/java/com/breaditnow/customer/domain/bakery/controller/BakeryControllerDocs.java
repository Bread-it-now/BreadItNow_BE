package com.breaditnow.customer.domain.bakery.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 빵집 API", description = "빵집 정보를 조회하거나 인기 빵집 목록을 확인하는 API입니다.")
public interface BakeryControllerDocs {

	@Operation(summary = "빵집 상세 정보 조회", description = "특정 bakery_id에 대한 빵집 상세 정보를 조회합니다.")
	@Parameter(name = "bakery_id", description = "조회할 빵집 ID", example = "100", required = true)
	@DomainErrorCodeExamples({BAKERY_INACTIVE, BAKERY_NOT_FOUND})
	ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(Long customerId, Long bakeryId);

	@Operation(
		summary = "인기 빵집 목록 조회",
		description = "고객 ID, 페이지, 정렬, 위치 정보를 기반으로 인기 빵집 목록을 페이지네이션 형태로 조회합니다."
	)
	@Parameters({
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
		@Parameter(name = "size", description = "한 페이지당 데이터 개수", example = "10", in = QUERY),
		@Parameter(name = "sort", description = "정렬 기준", example = "reservation", in = QUERY)
	})
	@DomainErrorCodeExamples({SORT_CONDITION_NOT_FOUND})
	ApiSuccessResponse<HotBakeryPageResponse> searchHotBakeries(
		Long customerId, int page, int size, String sort, GeoPointRequest geoPointRequest);
	
}
