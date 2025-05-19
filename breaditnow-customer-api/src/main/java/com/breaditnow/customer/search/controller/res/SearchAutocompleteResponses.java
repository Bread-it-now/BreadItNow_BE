package com.breaditnow.customer.search.controller.res;

import java.util.List;

import lombok.Builder;

@Builder
public record SearchAutocompleteResponses(
	List<SearchAutocompleteResponse> searchAutoCompletes
) {
	public static SearchAutocompleteResponses of(List<SearchAutocompleteResponse> searchAutocompleteResponse) {
		return SearchAutocompleteResponses.builder()
			.searchAutoCompletes(searchAutocompleteResponse)
			.build();
	}
}
