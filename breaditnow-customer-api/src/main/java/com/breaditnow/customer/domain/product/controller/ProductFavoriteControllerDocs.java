package com.breaditnow.customer.domain.product.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.req.GeoPointRequest;
import com.breaditnow.customer.domain.product.controller.res.ProductFavoritePageResponse;
import com.breaditnow.customer.global.swagger.annotation.DomainErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 상품 즐겨찾기 API", description = "특정 상품을 즐겨찾기 등록, 해제 및 조회하는 API입니다.")
public interface ProductFavoriteControllerDocs {

	@Operation(summary = "상품 즐겨찾기 등록", description = "특정 product_id에 대해 상품 즐겨찾기를 등록합니다.")
	@Parameter(name = "productId", description = "좋아요로 등록할 상품 ID", example = "100", required = true)
	@DomainErrorCodeExamples({PRODUCT_INACTIVE, PRODUCT_NOT_FOUND, CUSTOMER_NOT_FOUND, BREAD_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> likeProduct(Long customerId, Long productId);

	@Operation(summary = "상품 즐겨찾기 삭제", description = "특정 product_id에 대해 등록된 상품 즐겨찾기를 해제합니다.")
	@Parameter(name = "productId", description = "즐겨찾기 삭제할 상품 ID", example = "100", required = true)
	@DomainErrorCodeExamples({BREAD_FAVORITE_NOT_FOUND})
	ApiSuccessResponse<Map<String, Long>> deleteProduct(Long customerId, Long bakeryId);

	@Operation(summary = "즐겨찾기 상품 목록 조회", description = "즐겨찾기한 상품 목록을 페이지네이션 형태로 조회합니다.")
	@Parameters({
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
		@Parameter(name = "size", description = "한 페이지당 상품 개수", example = "10"),
		@Parameter(name = "sort", description = "정렬 기준", example = "latest")
	})
	ApiSuccessResponse<ProductFavoritePageResponse> getFavorites(Long customerId, int page, int size, String sort,
		GeoPointRequest geoPointRequest);
}
