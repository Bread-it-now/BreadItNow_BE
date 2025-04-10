package com.breaditnow.owner.domain.breadcategory.controller;

import java.util.List;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.breadcategory.controller.res.BreadCategoryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 빵 카테고리 API", description = "빵 카테고리 관련 정보를 조회하는 API입니다. 이 API는 키워드와 요청된 사이즈를 기반으로 관련 빵 카테고리 목록을 반환합니다. ")
public interface BreadCategoryControllerDocs {

	@Operation(
		summary = "빵 카테고리 검색",
		description = "입력된 키워드와 요청 사이즈를 기준으로 빵 카테고리 목록을 조회합니다(키워드를 입력하지 않으면 전체 빵 카테고리를 조회합니다).")
	@Parameters({
		@Parameter(name = "keyword", description = "빵 카테고리 이름", example = "식빵"),
		@Parameter(name = "size", description = "개수", example = "8")
	})
	ApiSuccessResponse<List<BreadCategoryResponse>> searchBreadCategory(String keyword, int size);
}
