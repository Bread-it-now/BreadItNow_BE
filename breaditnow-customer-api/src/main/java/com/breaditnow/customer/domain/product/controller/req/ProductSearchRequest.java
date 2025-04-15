package com.breaditnow.customer.domain.product.controller.req;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;

public record ProductSearchRequest(
	int page,
	int size,
	SortType sort,
	String keyword,
	Double latitude,
	Double longitude
) {
	public static ProductSearchRequest of(int page, int size, String sort, String keyword, Double latitude,
		Double longitude) {
		return new ProductSearchRequest(page, size, SortType.from(sort), keyword, latitude, longitude);
	}
}
