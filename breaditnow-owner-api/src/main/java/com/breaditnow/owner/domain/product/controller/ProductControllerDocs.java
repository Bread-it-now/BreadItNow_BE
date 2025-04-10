package com.breaditnow.owner.domain.product.controller;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static com.breaditnow.external.global.exception.ExternalErrorCode.*;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.product.controller.req.ProductCreateRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductDeleteRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductHiddenRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductOrderUpdateRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductStockUpdateRequest;
import com.breaditnow.owner.domain.product.controller.req.ProductUpdateRequest;
import com.breaditnow.owner.domain.product.controller.res.ProductListResponse;
import com.breaditnow.owner.domain.product.controller.res.ProductResponse;
import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;
import com.breaditnow.owner.global.swagger.annotation.ExternalErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 빵집 상품 API", description = "소유자가 빵집 상품을 생성, 수정, 삭제, 조회 및 재고/순서 업데이트하는 API입니다.")
public interface ProductControllerDocs {

	@Operation(
		summary = "상품 생성",
		description = "소유자와 빵집 ID를 기반으로 신규 상품을 생성합니다. 요청 데이터와 프로필 이미지를 받아 상품을 등록하고, 등록된 상품의 식별 정보를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, BREAD_CATEGORY_NOT_FOUND})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<Map<String, Long>> createBakeryProduct(
		Long ownerId,
		Long bakeryId,
		ProductCreateRequest request,
		MultipartFile productImage);

	@Operation(
		summary = "상품 수정",
		description = "소유자, 빵집, 상품 ID를 기반으로 기존 상품의 정보를 수정합니다. 업데이트된 요청 데이터를 적용하여 수정된 상품의 상세 정보를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<ProductResponse> updateBakeryProduct(
		Long ownerId,
		Long bakeryId,
		Long productId,
		ProductUpdateRequest request,
		MultipartFile productImage);

	@Operation(
		summary = "단일 상품 삭제",
		description = "소유자, 빵집, 상품 ID를 기반으로 단일 상품을 삭제하고, 삭제된 상품의 식별 정보를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> deleteBakeryProduct(
		Long ownerId,
		Long bakeryId,
		Long productId);

	@Operation(
		summary = "여러 상품 삭제",
		description = "소유자와 빵집 ID를 기반으로 여러 상품을 한 번에 삭제하고, 각 상품 삭제 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Integer>> deleteBakeryProducts(
		Long ownerId,
		Long bakeryId,
		ProductDeleteRequest productDeleteRequest);

	@Operation(
		summary = "상품 숨김 상태 업데이트",
		description = "소유자와 빵집 ID를 기반으로 상품의 숨김(표시 여부) 상태를 업데이트하고, 업데이트 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Integer>> updateHiddenBakeryProducts(
		Long ownerId,
		Long bakeryId,
		ProductHiddenRequest productHiddenRequest);

	@Operation(
		summary = "상품 상세 조회",
		description = "소유자, 빵집, 상품 ID를 기반으로 특정 상품의 상세 정보를 조회하여 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<ProductResponse> getBakeryProduct(
		Long ownerId,
		Long bakeryId,
		Long productId);

	@Operation(
		summary = "전체 상품 목록 조회",
		description = "소유자와 빵집 ID를 기반으로 전체 상품 목록을 조회합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
	ApiSuccessResponse<ProductListResponse> getBakeryProducts(
		Long ownerId,
		Long bakeryId);

	@Operation(
		summary = "상품 순서 업데이트",
		description = "소유자와 빵집 ID를 기반으로 상품의 순서를 업데이트하고, 업데이트 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<String> updateBakeryProductOrder(
		Long ownerId,
		Long bakeryId,
		ProductOrderUpdateRequest orderUpdateRequest);

	@Operation(
		summary = "상품 재고 업데이트",
		description = "소유자, 빵집, 상품 ID를 기반으로 상품의 재고 정보를 업데이트하고, 업데이트 결과를 반환합니다."
	)
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Integer>> updateProductStock(
		Long ownerId,
		Long bakeryId,
		Long productId,
		ProductStockUpdateRequest request);
}
