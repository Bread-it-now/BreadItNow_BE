package com.breaditnow.customer.domain.product.controller.res;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import com.breaditnow.customer.domain.bakery.controller.res.BreadCategoryResponse;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;

import lombok.Builder;

@Builder
public record ProductResponse(
	Long productId,
	Long bakeryId,
	ProductType productType,
	String name,
	int stock,
	int price,
	String image,
	String description,
	List<BreadCategoryResponse> breadCategories,
	List<String> releaseTimes,
	boolean alarmEnabled,
	boolean isFavorite,
	int displayOrder
) {

	public static ProductResponse of(Product product, boolean alarmEnabled, boolean isFavorite) {
		List<String> releaseTimes = product.getReleaseTime() != null
			? Arrays.stream(product.getReleaseTime().split(";"))
			.map(String::trim)
			.filter(time -> !time.isEmpty())
			.toList()
			: List.of();

		return ProductResponse.builder()
			.productId(product.getId())
			.bakeryId(product.getBakery().getId())
			.productType(product.getType())
			.name(product.getName())
			.price(product.getPrice())
			.stock(product.getStock())
			.image(product.getImage())
			.description(product.getDescription())
			.breadCategories(product.getBreadCategories().stream()
				.map(relation -> new BreadCategoryResponse(
					relation.getBreadCategory().getId(),
					relation.getBreadCategory().getName()))
				.collect(toList())
			)
			.releaseTimes(releaseTimes)
			.alarmEnabled(alarmEnabled)
			.isFavorite(isFavorite)
			.displayOrder(product.getDisplayOrder())
			.build();
	}
}
