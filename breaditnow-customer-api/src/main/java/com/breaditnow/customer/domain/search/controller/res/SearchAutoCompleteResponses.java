package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.search.enumerate.SearchType;

public record SearchAutoCompleteResponses(
	String name,
	SearchType type
) {
	public static SearchAutoCompleteResponses of(String name, SearchType type) {
		return new SearchAutoCompleteResponses(name, type);
	}
}
