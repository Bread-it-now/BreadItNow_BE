package com.breaditnow.customer.domain.bakery.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 빵집 좋아요 API")
public interface BakeryFavoriteControllerDocs {
	@Operation(summary = "고객 ID와 빵집 ID를 기반으로 빵집에 좋아요(즐겨찾기)를 등록합니다.")
	@DomainErrorCodeExamples({BAKERY_INACTIVE, BAKERY_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> likeBakery(Long customerId, Long bakeryId);

	@Operation(summary = "고객 ID와 빵집 ID를 기반으로 빵집 좋아요(즐겨찾기)를 삭제합니다.")
	@DomainErrorCodeExamples({BAKERY_FAVORITE_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteBakery(Long customerId, Long bakeryId);

	@Operation(summary = "고객 ID, 페이지, 정렬, 위치 정보를 기반으로 즐겨찾기한 빵집 목록을 조회합니다.")
	ApiSuccessResponse<BakeryFavoritePageResponse> getFavorites(Long customerId, int page, int size, String sort,
		GeoPointRequest geoPointRequest);
}
