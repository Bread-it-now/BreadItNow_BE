package com.breaditnow.customer.domain.product.controller.res;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record ProductResponse(
	Long productId,
	Long bakeryId,
	String productType,
	String name,
	int stock,
	int price,
	String image,
	String description,
	List<Long> categoryIds,
	List<String> releaseTimes,
	boolean alarmEnabled,
	boolean isFavorite
) {

	public static ProductResponse of(Product product, Long bakeryId, boolean alarmEnabled, boolean isFavorite) {
		List<String> releaseTimes = product.getReleaseTime() != null
			? Arrays.stream(product.getReleaseTime().split(";"))
			.map(String::trim)
			.filter(time -> !time.isEmpty())
			.toList()
			: List.of();

		return ProductResponse.builder()
			.productId(product.getId())
			.bakeryId(bakeryId)
			.productType(product.getType().name())
			.name(product.getName())
			.price(product.getPrice())
			.stock(product.getStock())
			.image(product.getImage())
			.description(product.getDescription())
			.categoryIds(product.getBreadCategories()
				.stream()
				.map(o -> o.getBreadCategory().getId())
				.collect(toList())
			)
			.releaseTimes(releaseTimes)
			.alarmEnabled(alarmEnabled)
			.isFavorite(isFavorite)
			.build();
	}
}
