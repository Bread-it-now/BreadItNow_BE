package com.breaditnow.customer.domain.product.controller.res;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record ProductFavoriteResponse(
	Long productId,
	Long bakeryId,
	String name,
	String image,
	int price,
	List<String> releaseTimes,
	boolean isBakeryActive,
	boolean isBreadActive
) {
	public static ProductFavoriteResponse of(Product product) {
		return ProductFavoriteResponse.builder()
			.productId(product.getId())
			.bakeryId(product.getBakery().getId())
			.name(product.getName())
			.image(product.getImage())
			.price(product.getPrice())
			.releaseTimes(Arrays.stream(product.getReleaseTime().split(";"))
				.map(String::trim)
				.collect(Collectors.toList())
			)
			.isBakeryActive(product.getBakery().isActive())
			.isBreadActive(product.isActive())
			.build();
	}
}
