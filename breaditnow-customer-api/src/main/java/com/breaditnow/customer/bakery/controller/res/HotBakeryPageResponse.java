package com.breaditnow.customer.bakery.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;

import lombok.Builder;

@Builder
public record HotBakeryPageResponse(
	List<HotBakeryResponse> hotBakeries,
	PageInfo pageInfo
) {
	public static HotBakeryPageResponse of(Page<BakeryDistanceDto> bakeryPage) {
		List<HotBakeryResponse> hotBakeries = bakeryPage
			.map(HotBakeryResponse::of)
			.getContent();

		PageInfo pageInfo = PageInfo.builder()
			.totalElements(bakeryPage.getTotalElements())
			.totalPages(bakeryPage.getTotalPages())
			.isLast(bakeryPage.isLast())
			.currPage(bakeryPage.getPageable().getPageNumber())
			.build();

		return HotBakeryPageResponse.builder()
			.hotBakeries(hotBakeries)
			.pageInfo(pageInfo)
			.build();
	}
}
