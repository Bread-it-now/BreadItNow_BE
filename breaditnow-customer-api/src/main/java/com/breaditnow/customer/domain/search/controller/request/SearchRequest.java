package com.breaditnow.customer.domain.search.controller.request;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;

public record SearchRequest(
	int page,
	int size,
	SortType sort,
	String keyword,
	Double latitude,
	Double longitude
) {
	public static SearchRequest of(int page, int size, String sort, String keyword, Double latitude,
		Double longitude) {
		return new SearchRequest(page, size, SortType.from(sort), keyword, latitude, longitude);
	}
}
