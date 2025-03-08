package com.breaditnow.customer.domain.product.controller.res;

import java.util.List;

import lombok.Builder;

@Builder
public record ProductResponse(
	Long productId,
	Long bakeryId,
	String productType,
	String name,
	int price,
	String image,
	String description,
	List<Long> breadCategoryIds,
	List<String> releaseTimes
) {
}
