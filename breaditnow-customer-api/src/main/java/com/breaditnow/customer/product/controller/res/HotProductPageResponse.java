package com.breaditnow.customer.product.controller.res;

import java.util.List;

import org.springframework.data.domain.Page;

import com.breaditnow.common.page.PageInfo;

import lombok.Builder;

@Builder
public record HotProductPageResponse(
	List<HotProductResponse> hotProducts,
	PageInfo pageInfo
) {
	public static HotProductPageResponse of(Page<HotProductResponse> hotProductResponses) {
		PageInfo pageInfo = PageInfo.builder()
			.totalElements(hotProductResponses.getTotalElements())
			.totalPages(hotProductResponses.getTotalPages())
			.isLast(hotProductResponses.isLast())
			.currPage(hotProductResponses.getPageable().getPageNumber())
			.build();

		return HotProductPageResponse.builder()
			.hotProducts(hotProductResponses.getContent())
			.pageInfo(pageInfo)
			.build();
	}
}
