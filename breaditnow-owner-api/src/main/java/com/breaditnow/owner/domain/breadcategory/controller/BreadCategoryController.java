package com.breaditnow.owner.domain.breadcategory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.breadcategory.controller.res.BreadCategoryResponse;
import com.breaditnow.owner.domain.breadcategory.service.BreadCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bread-category")
public class BreadCategoryController {

	private final BreadCategoryService breadCategoryService;

	@GetMapping("/search")
	public ApiSuccessResponse<List<BreadCategoryResponse>> searchBreadCategory(
		@RequestParam(value = "keyword", required = false) String keyword,
		@RequestParam(name = "size", defaultValue = "8") int size
	) {
		return ApiSuccessResponse.of(breadCategoryService.searchBreadCategories(keyword, size));
	}
}
