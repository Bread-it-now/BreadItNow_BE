package com.breaditnow.domain.domain.search.repository;

import java.util.List;

import com.breaditnow.domain.global.dto.AutoCompleteDto;

public interface AutocompleteJdbcDao {
	List<AutoCompleteDto> findByKeywordMatch(String keyword);
}
