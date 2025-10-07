package com.breaditnow.common.swagger.docs;

import com.breaditnow.alert.application.response.ProductAlertToggleResponse;
import com.breaditnow.alert.presentation.request.ProductAlertSearchRequest;
import com.breaditnow.alert.presentation.response.ProductAlertPageResponse;
import com.breaditnow.alert.presentation.response.TodayProductAlertListResponse;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

public interface ProductAlertControllerDocs {
    @Operation(summary = "상품 알림 등록", description = "특정 상품에 대한 알림을 등록합니다.")
    @Parameter(name = "productId", description = "알림 등록할 상품 ID", example = "1001", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/product/{productId}")
    ApiSuccessResponse<Void> registerProductAlert(@AuthCustomer Long customerId, @PathVariable("productId") Long productId);

    @Operation(summary = "상품 알림 삭제", description = "특정 상품에 대한 알림을 삭제합니다.")
    @Parameter(name = "productId", description = "알림 삭제할 상품 ID", example = "1001", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/product/{productId}")
    ApiSuccessResponse<Void> deleteProductAlert(@AuthCustomer Long customerId, @PathVariable("productId") Long productId);

    @Operation(summary = "상품 알림 토글", description = "특정 상품에 대한 알림을 토글합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토글 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/product/{productId}/toggle")
    ApiSuccessResponse<ProductAlertToggleResponse> toggleProductAlert(@AuthCustomer Long customerId, @PathVariable("productId") Long productId);

    @Operation(summary = "오늘의 상품 알림 조회", description = "오늘의 상품 알림 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/today")
    ApiSuccessResponse<TodayProductAlertListResponse> getTodayProductAlert(@AuthCustomer Long customerId);

    @Operation(summary = "상품 알림 목록 조회", description = "상품 알림 목록을 페이징하여 조회합니다.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
            @Parameter(name = "size", description = "한 페이지당 최대 데이터 개수", example = "10", in = QUERY)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/product")
    ApiSuccessResponse<ProductAlertPageResponse> getProductAlerts(@AuthCustomer Long customerId, ProductAlertSearchRequest request);
}
