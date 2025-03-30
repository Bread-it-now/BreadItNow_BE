package com.breaditnow.customer.domain.product.controller.res;

import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record HotProductResponse(
	Long productId,
	Long bakeryId,
	String name,
	String image,
	Integer price,
	Integer stock
) {
	public static HotProductResponse of(Product product) {
		return HotProductResponse.builder()
			.productId(product.getId())
			.bakeryId(product.getBakery().getId())
			.name(product.getName())
			.image(product.getImage())
			.price(product.getPrice())
			.stock(product.getStock())
			.build();
	}
}
