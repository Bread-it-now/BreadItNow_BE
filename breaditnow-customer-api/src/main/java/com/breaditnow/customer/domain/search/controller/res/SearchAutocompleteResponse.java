package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.search.enumerate.SearchType;
import com.breaditnow.domain.global.dto.AutoCompleteDto;

import lombok.Builder;

@Builder
public record SearchAutocompleteResponse(
	String name,
	SearchType type
) {
	public static SearchAutocompleteResponse of(AutoCompleteDto autoComplete) {
		return SearchAutocompleteResponse.builder()
			.name(autoComplete.name())
			.type(autoComplete.searchType())
			.build();
	}
}
