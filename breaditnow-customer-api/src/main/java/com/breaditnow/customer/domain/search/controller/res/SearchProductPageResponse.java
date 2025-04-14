package com.breaditnow.customer.domain.search.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;

import lombok.Builder;

@Builder
public record SearchProductPageResponse(
	List<SearchProductResponse> searchProducts,
	PageInfo pageInfo
) {
	public static SearchProductPageResponse of(Page<SearchProductResponse> searchProductResponses) {
		PageInfo pageInfo = PageInfo.builder()
			.totalElements(searchProductResponses.getTotalElements())
			.totalPages(searchProductResponses.getTotalPages())
			.isLast(searchProductResponses.isLast())
			.currPage(searchProductResponses.getPageable().getPageNumber())
			.build();

		return SearchProductPageResponse.builder()
			.searchProducts(searchProductResponses.getContent())
			.pageInfo(pageInfo)
			.build();
	}
}
