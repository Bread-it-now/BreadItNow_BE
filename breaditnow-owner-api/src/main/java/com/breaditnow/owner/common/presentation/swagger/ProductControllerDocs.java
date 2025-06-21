//package com.breaditnow.owner.domain.product.controller;
//
//import com.breaditnow.common.response.ApiSuccessResponse;
//import com.breaditnow.owner.domain.product.controller.req.*;
//import com.breaditnow.owner.domain.product.controller.res.ProductListResponse;
//import com.breaditnow.owner.domain.product.controller.res.ProductResponse;
//import com.breaditnow.owner.global.swagger.annotation.DomainErrorCodeExamples;
//import com.breaditnow.owner.global.swagger.annotation.ExternalErrorCodeExamples;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//
//import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
//import static com.breaditnow.external.global.exception.ExternalErrorCode.FILE_CREATION_FAILED;
//
//@Tag(name = "Owner - 빵집 상품 API", description = "소유자가 빵집의 상품을 생성, 수정, 삭제, 조회하는 API입니다.")
//public interface ProductControllerDocs {
//
//    @Operation(summary = "상품 등록", description = "특정 빵집(bakeryId)에 새 상품을 등록합니다.")
//    @Parameter(name = "bakeryId", description = "상품을 등록할 빵집 ID", example = "1", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, BREAD_CATEGORY_NOT_FOUND})
//    @ExternalErrorCodeExamples({FILE_CREATION_FAILED})
//    ApiSuccessResponse<Map<String, Long>> createBakeryProduct(Long ownerId, Long bakeryId, ProductCreateRequest request,
//                                                              MultipartFile productImage);
//
//    @Operation(summary = "상품 수정", description = "특정 빵집(bakeryId)의 상품(productId)을 수정합니다.")
//    @Parameter(name = "bakeryId", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @Parameter(name = "productId", description = "수정할 상품 ID", example = "10", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    @ExternalErrorCodeExamples({FILE_CREATION_FAILED})
//    ApiSuccessResponse<ProductResponse> updateBakeryProduct(Long ownerId, Long bakeryId, Long productId,
//                                                            ProductUpdateRequest request, MultipartFile productImage);
//
//    @Operation(summary = "상품 단건 삭제", description = "특정 빵집(bakeryId)의 상품(productId)을 삭제합니다.")
//    @Parameter(name = "bakeryId", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @Parameter(name = "productId", description = "삭제할 상품 ID", example = "10", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    ApiSuccessResponse<Map<String, Long>> deleteBakeryProduct(Long ownerId, Long bakeryId, Long productId);
//
//    @Operation(summary = "상품 다건 삭제", description = "특정 빵집(bakeryId)의 복수의 상품을 한 번에 삭제합니다.")
//    @Parameter(name = "bakeryId", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    ApiSuccessResponse<Map<String, Integer>> deleteBakeryProducts(Long ownerId, Long bakeryId,
//                                                                  ProductDeleteRequest productDeleteRequest);
//
//    @Operation(summary = "상품 숨김/공개 처리", description = "특정 빵집의 상품들을 숨김처리(또는 숨김 해제)합니다.")
//    @Parameter(name = "bakeryId", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    ApiSuccessResponse<Map<String, Integer>> updateHiddenProducts(Long ownerId, Long bakeryId,
//                                                                  ProductHiddenRequest productHiddenRequest);
//
//    @Operation(summary = "상품 단건 조회", description = "특정 빵집의 단일 상품 정보를 조회합니다.")
//    @Parameter(name = "bakeryId", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @Parameter(name = "productId", description = "조회할 상품 ID", example = "10", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    ApiSuccessResponse<ProductResponse> getBakeryProduct(Long ownerId, Long bakeryId, Long productId);
//
//    @Operation(summary = "상품 목록 조회", description = "특정 빵집에 등록된 모든 상품 목록을 조회합니다.")
//    @Parameter(name = "bakeryId", description = "상품 목록을 조회할 빵집 ID", example = "1", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH})
//    ApiSuccessResponse<ProductListResponse> getBakeryProducts(Long ownerId, Long bakeryId);
//
//    @Operation(summary = "상품 정렬 순서 변경", description = "특정 빵집의 상품 노출 순서를 변경합니다.")
//    @Parameter(name = "bakeryId", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    ApiSuccessResponse<String> updateBakeryProductOrder(Long ownerId, Long bakeryId,
//                                                        ProductOrderUpdateRequest orderUpdateRequest);
//
//    @Operation(summary = "상품 재고 변경", description = "특정 상품의 재고 수량을 변경합니다.")
//    @Parameter(name = "bakery_id", description = "상품이 속한 빵집 ID", example = "1", required = true)
//    @Parameter(name = "product_id", description = "재고를 변경할 상품 ID", example = "10", required = true)
//    @DomainErrorCodeExamples({BAKERY_NOT_FOUND, BAKERY_INACTIVE, OWNER_MISMATCH, PRODUCT_NOT_FOUND, PRODUCT_INACTIVE,
//            PRODUCT_MISMATCH})
//    ApiSuccessResponse<Map<String, Integer>> updateProductStock(Long ownerId, Long bakeryId, Long productId,
//                                                                ProductStockUpdateRequest request);
//}
