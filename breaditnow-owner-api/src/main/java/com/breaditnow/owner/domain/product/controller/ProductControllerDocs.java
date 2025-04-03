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

@Tag(name = "Owner - 빵집 상품 API")
public interface ProductControllerDocs {
	@Operation(summary = "소유자와 빵집 ID를 기반으로 상품을 생성합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, BREAD_CATEGORY_NOT_FOUND})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<Map<String, Long>> createBakeryProduct(Long ownerId, Long bakeryId, ProductCreateRequest request,
		MultipartFile productImage);

	@Operation(summary = "소유자, 빵집, 상품 ID를 기반으로 상품 정보를 수정합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	@ExternalErrorCodeExamples({FILE_CREATION_FAILED})
	ApiSuccessResponse<ProductResponse> updateBakeryProduct(Long ownerId, Long bakeryId, Long productId,
		ProductUpdateRequest request, MultipartFile productImage);

	@Operation(summary = "소유자, 빵집, 상품 ID를 기반으로 단일 상품을 삭제합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Long>> deleteBakeryProduct(Long ownerId, Long bakeryId, Long productId);

	@Operation(summary = "소유자와 빵집 ID를 기반으로 여러 상품을 삭제합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Integer>> deleteBakeryProducts(Long ownerId, Long bakeryId,
		ProductDeleteRequest productDeleteRequest);

	@Operation(summary = "소유자와 빵집 ID를 기반으로 상품의 숨김 상태를 업데이트합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Integer>> updateHiddenBakeryProducts(Long ownerId, Long bakeryId,
		ProductHiddenRequest productHiddenRequest);

	@Operation(summary = "소유자, 빵집, 상품 ID를 기반으로 상품 상세 정보를 조회합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<ProductResponse> getBakeryProduct(Long ownerId, Long bakeryId, Long productId);

	@Operation(summary = "소유자와 빵집 ID를 기반으로 전체 상품 목록을 조회합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
	ApiSuccessResponse<ProductListResponse> getBakeryProducts(Long ownerId, Long bakeryId);

	@Operation(summary = "소유자와 빵집 ID를 기반으로 상품 순서를 업데이트합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<String> updateBakeryProductOrder(Long ownerId, Long bakeryId,
		ProductOrderUpdateRequest orderUpdateRequest);

	@Operation(summary = "소유자, 빵집, 상품 ID를 기반으로 상품 재고를 업데이트합니다.")
	@DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
		PRODUCT_MISMATCH})
	ApiSuccessResponse<Map<String, Integer>> updateProductStock(Long ownerId, Long bakeryId, Long productId,
		ProductStockUpdateRequest request);
}
