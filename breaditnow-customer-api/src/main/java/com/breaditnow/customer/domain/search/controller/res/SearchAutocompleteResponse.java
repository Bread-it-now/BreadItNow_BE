package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.search.entity.Autocomplete;
import com.breaditnow.domain.domain.search.enumerate.SearchType;

import lombok.Builder;

@Builder
public record SearchAutocompleteResponse(
	String name,
	SearchType type
) {
	public static SearchAutocompleteResponse of(Autocomplete autoComplete) {
		return SearchAutocompleteResponse.builder()
			.name(autoComplete.getName())
			.type(autoComplete.getSearchType())
			.build();
	}
}
