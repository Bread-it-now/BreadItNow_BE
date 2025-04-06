package com.breaditnow.customer.domain.bakery.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 빵집 좋아요 API", description = "고객이 빵집을 즐겨찾기 하는 기능 관련 API")
public interface BakeryFavoriteControllerDocs {

	@Operation(
		summary = "빵집 즐겨찾기 등록",
		description = "고객 ID와 빵집 ID를 기반으로 빵집에 즐겨찾기를 등록합니다."
	)
	@DomainErrorCodeExamples({BAKERY_INACTIVE, BAKERY_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> likeBakery(Long customerId, Long bakeryId);

	@Operation(
		summary = "빵집 즐겨찾기 삭제",
		description = "고객 ID와 빵집 ID를 기반으로 빵집에 등록된 즐겨찾기를 삭제합니다."
	)
	@DomainErrorCodeExamples({BAKERY_FAVORITE_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteBakery(Long customerId, Long bakeryId);

	@Operation(
		summary = "즐겨찾기 빵집 목록 조회",
		description = "고객 ID, 페이지, 정렬, 위치 정보를 기반으로 즐겨찾기한 빵집 목록을 조회합니다."
	)
	ApiSuccessResponse<BakeryFavoritePageResponse> getFavorites(
		Long customerId, int page, int size, String sort, GeoPointRequest geoPointRequest);
}
