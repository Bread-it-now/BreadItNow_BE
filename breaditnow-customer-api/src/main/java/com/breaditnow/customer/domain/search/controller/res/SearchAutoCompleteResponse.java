package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.search.entity.AutoComplete;
import com.breaditnow.domain.domain.search.enumerate.SearchType;

import lombok.Builder;

@Builder
public record SearchAutoCompleteResponse(
	String name,
	SearchType type
) {
	public static SearchAutoCompleteResponse of(AutoComplete autoComplete) {
		return SearchAutoCompleteResponse.builder()
			.name(autoComplete.getName())
			.type(autoComplete.getSearchType())
			.build();
	}
}
