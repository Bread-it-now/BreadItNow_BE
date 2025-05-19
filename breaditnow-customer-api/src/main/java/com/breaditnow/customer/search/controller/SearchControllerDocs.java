package com.breaditnow.customer.search.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.product.controller.res.SearchProductPageResponse;
import com.breaditnow.customer.search.controller.res.SearchAutocompleteResponses;
import com.breaditnow.customer.search.controller.res.SearchBakeryPageResponse;
import com.breaditnow.customer.common.swagger.annotation.DomainErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.breaditnow.domain.global.exception.DomainErrorCode.SORT_CONDITION_NOT_FOUND;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Customer - 검색 API", description = "빵집 및 상품 검색 API입니다.")
public interface SearchControllerDocs {
    @Operation(
            summary = "빵집 검색",
            description = "입력한 검색어와 위치 정보를 기반으로 빵집 목록을 페이지네이션 형태로 조회합니다."
    )
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
            @Parameter(name = "size", description = "한 페이지당 데이터 개수", example = "10", in = QUERY),
            @Parameter(name = "sort", description = "정렬 기준", example = "latest", in = QUERY),
            @Parameter(name = "keyword", description = "검색에 사용할 키워드", example = "오븐", in = QUERY),
            @Parameter(name = "latitude", description = "현재 위치의 위도 정보", example = "37.12345", in = QUERY),
            @Parameter(name = "longitude", description = "현재 위치의 경도 정보", example = "127.12345", in = QUERY)
    })
    @DomainErrorCodeExamples({SORT_CONDITION_NOT_FOUND})
    ApiSuccessResponse<SearchBakeryPageResponse> searchBakeries(Long customerId, int page, int size, String sort,
                                                                String keyword, Double latitude, Double longitude);

    @Operation(summary = "상품 검색", description = "키워드 및 위치 정보를 기반으로 상품 목록을 페이지네이션 형태로 조회합니다.")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0", in = QUERY),
            @Parameter(name = "size", description = "한 페이지당 노출할 상품 개수", example = "10", in = QUERY),
            @Parameter(name = "sort", description = "정렬 기준", example = "latest", in = QUERY),
            @Parameter(name = "keyword", description = "검색할 상품 키워드", example = "빵", in = QUERY),
            @Parameter(name = "latitude", description = "현재 위치의 위도", example = "37.12345", in = QUERY),
            @Parameter(name = "longitude", description = "현재 위치의 경도", example = "127.12345", in = QUERY)
    })
    ApiSuccessResponse<SearchProductPageResponse> searchProducts(Long customerId, int page, int size, String sort,
                                                                 String keyword, Double latitude, Double longitude);

    @Operation(summary = "자동완성 검색", description = "입력한 키워드를 기반으로 빵집 및 상품에 대한 상위 10개의 자동완성 추천 목록을 조회한다")
    @Parameter(name = "keyword", description = "자동완성에 사용할 검색어", example = "크림", in = QUERY)
    ApiSuccessResponse<SearchAutocompleteResponses> searchAutoComplete(String keyword);

}
