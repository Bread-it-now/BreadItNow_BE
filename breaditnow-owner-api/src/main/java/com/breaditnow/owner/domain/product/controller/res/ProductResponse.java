package com.breaditnow.owner.domain.product.controller.res;

import java.util.List;

import com.breaditnow.domain.domain.product.entity.Product;

public record ProductResponse(
	Long id,
	Long bakeryId,
	String productType,
	String name,
	int price,
	String image,
	String description,
	String releaseTime,
	int stock,
	boolean isActive,
	List<BreadCategoryResponse> breadCategories
) {
	public static ProductResponse of(Product product) {
		List<BreadCategoryResponse> breadCategoryResponses = product.getBreadCategories().stream()
			.map(relation -> new BreadCategoryResponse(
				relation.getBreadCategory().getId(),
				relation.getBreadCategory().getName()))
			.toList();
		
		return new ProductResponse(
			product.getId(),
			product.getBakery().getId(),
			product.getType().name(),
			product.getName(),
			product.getPrice(),
			product.getImage(),
			product.getDescription(),
			product.getReleaseTime(),
			product.getStock(),
			product.isActive(),
			breadCategoryResponses
		);
	}
}
