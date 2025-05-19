package com.breaditnow.customer.search.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;

import lombok.Builder;

@Builder
public record SearchBakeryPageResponse(
	List<SearchBakeryResponse> searchBakeries,
	PageInfo pageInfo
) {
	public static SearchBakeryPageResponse of(Page<BakeryDistanceDto> bakeryPage) {
		List<SearchBakeryResponse> bakeries = bakeryPage
			.map(SearchBakeryResponse::of)
			.getContent();

		PageInfo pageInfo = PageInfo.of(
			bakeryPage.getTotalElements(),
			bakeryPage.getTotalPages(),
			bakeryPage.isLast(),
			bakeryPage.getPageable().getPageNumber()
		);

		return SearchBakeryPageResponse.builder()
			.searchBakeries(bakeries)
			.pageInfo(pageInfo)
			.build();
	}
}
