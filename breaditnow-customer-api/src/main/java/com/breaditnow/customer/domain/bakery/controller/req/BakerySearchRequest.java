package com.breaditnow.customer.domain.bakery.controller.req;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;

public record BakerySearchRequest(
	int page,
	int size,
	SortType sort,
	String keyword,
	Double latitude,
	Double longitude
) {
	public static BakerySearchRequest of(int page, int size, String sort, String keyword, Double latitude,
		Double longitude) {
		return new BakerySearchRequest(page, size, SortType.from(sort), keyword, latitude, longitude);
	}
}
