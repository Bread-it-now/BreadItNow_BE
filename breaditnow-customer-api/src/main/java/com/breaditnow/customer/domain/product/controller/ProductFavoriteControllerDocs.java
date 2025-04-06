package com.breaditnow.customer.domain.product.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.product.controller.res.ProductFavoritePageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 좋아요 API", description = "고객이 상품에 좋아요(즐겨찾기)를 등록, 삭제하고 즐겨찾기한 상품 목록을 조회하는 API")
public interface ProductFavoriteControllerDocs {

	@Operation(
		summary = "상품 좋아요 등록",
		description = "고객 ID와 상품 ID를 기반으로 해당 상품에 좋아요(즐겨찾기)를 등록합니다."
	)
	@DomainErrorCodeExamples({PRODUCT_INACTIVE, PRODUCT_NOT_FOUND, CUSTOMER_NOT_FOUND, BREAD_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> likeProduct(Long customerId, Long productId);

	@Operation(
		summary = "상품 좋아요 삭제",
		description = "고객 ID와 빵집 ID를 기반으로 해당 상품에 등록된 좋아요(즐겨찾기)를 삭제합니다."
	)
	@DomainErrorCodeExamples({BREAD_FAVORITE_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteProduct(Long customerId, Long bakeryId);

	@Operation(
		summary = "즐겨찾기 상품 목록 조회",
		description = "고객 ID, 페이지, 사이즈, 정렬 기준 및 위치 정보를 기반으로 즐겨찾기한 상품 목록을 조회합니다."
	)
	ApiSuccessResponse<ProductFavoritePageResponse> getFavorites(Long customerId, int page, int size, String sort,
		GeoPointRequest geoPointRequest);
}
