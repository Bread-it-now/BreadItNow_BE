package com.breaditnow.customer.common.presentation.swagger.docs;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.bakery.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.common.presentation.swagger.annotation.DomainErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Customer - 빵집 즐겨찾기 API", description = "좋아하는(즐겨찾기) 빵집을 등록, 삭제, 조회하는 API입니다.")
public interface BakeryFavoriteControllerDocs {

    @Operation(summary = "빵집 즐겨찾기 등록", description = "특정 bakery_id에 대한 즐겨찾기를 등록합니다.")
    @Parameter(name = "bakeryId", description = "즐겨찾기에 추가할 빵집 ID", example = "100", required = true)
    @DomainErrorCodeExamples({BAKERY_INACTIVE, BAKERY_NOT_FOUND})
    ApiSuccessResponse<Map<String, Long>> likeBakery(Long customerId, Long bakeryId);

    @Operation(summary = "빵집 즐겨찾기 삭제", description = "고객 ID와 빵집 ID를 기반으로 빵집에 등록된 즐겨찾기를 삭제합니다.")
    @Parameter(name = "bakeryId", description = "즐겨찾기를 삭제할 빵집 ID", example = "100", required = true)
    @DomainErrorCodeExamples({BAKERY_FAVORITE_NOT_FOUND})
    ApiSuccessResponse<Map<String, Long>> deleteBakery(Long customerId, Long bakeryId);

    @Operation(summary = "즐겨찾기 빵집 목록 조회", description = "즐겨찾기한 빵집 목록을 페이지네이션 형태로 조회합니다.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
            @Parameter(name = "size", description = "한 페이지당 데이터 개수", example = "10", in = QUERY),
            @Parameter(name = "sort", description = "정렬 기준", example = "latest", in = QUERY)
    })
    ApiSuccessResponse<BakeryFavoritePageResponse> getFavorites(Long customerId, int page, int size, String sort,
                                                                GeoPointRequest geoPointRequest);
}
