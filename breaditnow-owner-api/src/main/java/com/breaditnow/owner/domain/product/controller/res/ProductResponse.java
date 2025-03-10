package com.breaditnow.owner.domain.product.controller.res;

import java.util.Arrays;
import java.util.List;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponse(
	Long id,
	Long bakeryId,
	String productType,
	String name,
	int price,
	String image,
	String description,
	List<String> releaseTimes,
	int stock,
	boolean isActive,
	List<BreadCategoryResponse> breadCategories,
	int displayOrder
) {
	public static ProductResponse of(Product product) {
		List<BreadCategoryResponse> breadCategoryResponses = null;
		if (product.getType() == ProductType.BREAD) {
			breadCategoryResponses = product.getBreadCategories().stream()
				.map(relation -> new BreadCategoryResponse(
					relation.getBreadCategory().getId(),
					relation.getBreadCategory().getName()))
				.toList();
		}

		List<String> releaseTimes = null;
		if (product.getReleaseTime() != null) {
			releaseTimes = Arrays.stream(product.getReleaseTime().split(";"))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.toList();
		}

		return new ProductResponse(
			product.getId(),
			product.getBakery().getId(),
			product.getType().name(),
			product.getName(),
			product.getPrice(),
			product.getImage(),
			product.getDescription(),
			releaseTimes,
			product.getStock(),
			product.isActive(),
			breadCategoryResponses,
			product.getDisplayOrder()
		);
	}
}
