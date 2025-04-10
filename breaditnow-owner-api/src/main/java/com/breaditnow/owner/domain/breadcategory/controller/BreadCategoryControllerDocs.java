package com.breaditnow.owner.domain.breadcategory.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import java.util.List;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.breadcategory.controller.res.BreadCategoryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 빵 카테고리 API", description = "빵 카테고리 관리 및 검색을 위한 API입니다.")
public interface BreadCategoryControllerDocs {

	@Operation(summary = "빵 카테고리 검색", description = "빵 카테고리를 키워드로 검색합니다.")
	@Parameter(name = "keyword", description = "검색 키워드 (예: 식빵, 소금빵)", example = "식빵", in = QUERY)
	@Parameter(name = "size", description = "검색 결과 개수", example = "8", in = QUERY)
	ApiSuccessResponse<List<BreadCategoryResponse>> searchBreadCategory(String keyword, int size);
}
