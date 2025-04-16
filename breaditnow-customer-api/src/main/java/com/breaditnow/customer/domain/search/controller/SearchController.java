package com.breaditnow.customer.domain.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.search.controller.res.SearchAutoCompleteResponse;
import com.breaditnow.customer.domain.search.service.AutoCompleteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
	private final AutoCompleteService searchService;

	@GetMapping("/autocomplete")
	public ApiSuccessResponse<SearchAutoCompleteResponse> searchAutoComplete(@RequestParam("keyword") String keyword) {
		return ApiSuccessResponse.of(searchService.searchAutoComplete(keyword));
	}
}
