package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record SearchProductResponse(
	Long productId,
	Long bakeryId,
	String bakeryName,
	String productName,
	String image,
	Integer price,
	Boolean isFavorite,
	Integer stock
) {
	public static SearchProductResponse of(Product product, Boolean isFavorite) {
		return SearchProductResponse.builder()
			.productId(product.getId())
			.bakeryId(product.getBakery().getId())
			.bakeryName(product.getBakery().getName())
			.productName(product.getName())
			.image(product.getImage())
			.price(product.getPrice())
			.isFavorite(isFavorite)
			.stock(product.getStock())
			.build();
	}
}
