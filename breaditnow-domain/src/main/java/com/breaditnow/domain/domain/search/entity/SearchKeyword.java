package com.breaditnow.domain.domain.search.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchKeyword {
	private final String keyword;

	public String toBooleanModeQuery() {
		String trimmed = keyword.trim();
		if (trimmed.isEmpty()) {
			return "";
		}
		
		return Arrays.stream(trimmed.split("\\s+"))
			.map(token -> "+" + token)
			.collect(Collectors.joining(" "));
	}
}
