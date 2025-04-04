package com.breaditnow.owner.domain.breadcategory.controller;

import java.util.List;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.breadcategory.controller.res.BreadCategoryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Owner - 빵 카테고리 API")
public interface BreadCategoryControllerDocs {
	@Operation(summary = "빵 카테고리 검색", description = "키워드와 사이즈를 기반으로 빵 카테고리 목록을 조회합니다.")
	ApiSuccessResponse<List<BreadCategoryResponse>> searchBreadCategory(String keyword, int size);
}
