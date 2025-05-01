package com.breaditnow.domain.domain.search.entity;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.breaditnow.domain.global.exception.DomainException;

import lombok.Getter;

@Getter
public class SearchKeyword {
	private final String keyword;

	public SearchKeyword(String keyword) {
		validateKeyword(keyword);
		this.keyword = keyword;
	}

	public String toBooleanModeQuery() {
		String trimmed = keyword.trim();

		return Arrays.stream(trimmed.split("\\s+"))
			.map(token -> "+" + token)
			.collect(Collectors.joining(" "));
	}

	private void validateKeyword(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			throw new DomainException(SEARCH_KEYWORD_TOO_SHORT);
		}
	}
}
