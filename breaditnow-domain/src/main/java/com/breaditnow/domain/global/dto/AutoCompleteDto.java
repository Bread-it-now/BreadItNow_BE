package com.breaditnow.domain.global.dto;

import com.breaditnow.domain.domain.search.enumerate.SearchType;

public record AutoCompleteDto(String name, SearchType searchType) {
}

