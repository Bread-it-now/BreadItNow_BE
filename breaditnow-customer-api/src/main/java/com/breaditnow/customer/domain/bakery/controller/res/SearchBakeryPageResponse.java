package com.breaditnow.customer.domain.bakery.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;

import lombok.Builder;

@Builder
public record SearchBakeryPageResponse(
	List<SearchBakeryResponse> hotBakeries,
	PageInfo pageInfo
) {
	public static SearchBakeryPageResponse of(Page<BakeryDistanceDto> bakeryPage) {
		List<SearchBakeryResponse> hotBakeries = bakeryPage
			.map(SearchBakeryResponse::of)
			.getContent();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(bakeryPage.getTotalElements())
			.totalPages(bakeryPage.getTotalPages())
			.isLast(bakeryPage.isLast())
			.currPage(bakeryPage.getPageable().getPageNumber())
			.build();

		return SearchBakeryPageResponse.builder()
			.hotBakeries(hotBakeries)
			.pageInfo(pageInfo)
			.build();
	}
}
