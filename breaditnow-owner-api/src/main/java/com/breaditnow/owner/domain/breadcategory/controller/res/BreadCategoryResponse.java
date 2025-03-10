package com.breaditnow.owner.domain.breadcategory.controller.res;

import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;

public record BreadCategoryResponse(
	Long categoryId,
	String categoryName
) {
	public static BreadCategoryResponse of(BreadCategory category) {
		return new BreadCategoryResponse(category.getId(), category.getName());
	}
}
