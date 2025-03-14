package com.breaditnow.customer.domain.product.controller.res;

import java.util.Arrays;
import java.util.List;

import com.breaditnow.customer.domain.bakery.controller.res.BreadCategoryResponse;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponse(
	Long productId,
	Long bakeryId,
	ProductType productType,
	String name,
	int stock,
	int price,
	String image,
	String description,
	Boolean isActive,
	List<BreadCategoryResponse> breadCategories,
	List<String> releaseTimes,
	Boolean alarmEnabled,
	Boolean isFavorite,
	Integer displayOrder
) {

	public static ProductResponse of(Product product, boolean alarmEnabled, boolean isFavorite) {
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

		return ProductResponse.builder()
			.productId(product.getId())
			.bakeryId(product.getBakery().getId())
			.productType(product.getType())
			.name(product.getName())
			.price(product.getPrice())
			.stock(product.getStock())
			.image(product.getImage())
			.description(product.getDescription())
			.breadCategories(breadCategoryResponses)
			.releaseTimes(releaseTimes)
			.alarmEnabled(alarmEnabled)
			.isFavorite(isFavorite)
			.displayOrder(product.getDisplayOrder())
			.isActive(product.isActive())
			.build();
	}
}
