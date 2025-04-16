package com.breaditnow.customer.domain.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.product.controller.res.SearchProductPageResponse;
import com.breaditnow.customer.domain.search.controller.req.SearchRequest;
import com.breaditnow.customer.domain.search.controller.res.SearchAutoCompleteResponse;
import com.breaditnow.customer.domain.search.controller.res.SearchBakeryPageResponse;
import com.breaditnow.customer.domain.search.service.SearchService;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
	private final SearchService searchService;

	@GetMapping("/bakery")
	public ApiSuccessResponse<SearchBakeryPageResponse> searchBakeries(
		@AuthCustomer Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "latest") String sort,
		@RequestParam(name = "keyword", defaultValue = "") String keyword,
		@RequestParam(name = "latitude", required = false) Double latitude,
		@RequestParam(name = "longitude", required = false) Double longitude) {
		return ApiSuccessResponse.of(searchService.searchBakeries(customerId,
			SearchRequest.of(page, size, sort, keyword, latitude, longitude)));
	}

	@GetMapping("/product")
	public ApiSuccessResponse<SearchProductPageResponse> searchProducts(
		@AuthCustomer Long customerId,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "sort", defaultValue = "latest") String sort,
		@RequestParam(name = "keyword", defaultValue = "") String keyword,
		@RequestParam(name = "latitude", required = false) Double latitude,
		@RequestParam(name = "longitude", required = false) Double longitude) {
		return ApiSuccessResponse.of(searchService.searchProducts(customerId,
			SearchRequest.of(page, size, sort, keyword, latitude, longitude)));
	}

	@GetMapping("/autocomplete")
	public ApiSuccessResponse<List<SearchAutoCompleteResponse>> searchAutoComplete(
		@RequestParam("keyword") String keyword) {
		return ApiSuccessResponse.of(searchService.searchAutoComplete(keyword));
	}
}
