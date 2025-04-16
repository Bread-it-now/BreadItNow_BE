package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.search.enumerate.SearchType;

public record SearchAutoCompleteResponse(
	String name,
	SearchType type
) {
	public static SearchAutoCompleteResponse of(String name, SearchType type) {
		return new SearchAutoCompleteResponse(name, type);
	}
}
