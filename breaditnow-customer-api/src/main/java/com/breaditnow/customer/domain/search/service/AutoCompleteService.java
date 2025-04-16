package com.breaditnow.customer.domain.search.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.search.controller.res.SearchAutoCompleteResponse;
import com.breaditnow.domain.domain.search.AutoCompleteRepository;
import com.breaditnow.domain.domain.search.entity.AutoComplete;
import com.breaditnow.domain.domain.search.entity.SearchKeyword;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AutoCompleteService {
	private final AutoCompleteRepository autoCompleteRepository;

	public SearchAutoCompleteResponse searchAutoComplete(String keyword) {
		SearchKeyword searchKeyword = new SearchKeyword(keyword);
		List<AutoComplete> autoCompletes = autoCompleteRepository.findByKeywordMatch(
			searchKeyword.toBooleanModeQuery());
		
	}
}
