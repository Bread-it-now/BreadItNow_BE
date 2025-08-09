package com.breaditnow.product.controller.res;

import com.breaditnow.common.domain.PageInfo;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record SearchProductPageResponse(
	List<SearchProductResponse> searchProducts,
	PageInfo pageInfo
) {
	public static SearchProductPageResponse of(Page<SearchProductResponse> searchProductResponses) {
		PageInfo pageInfo = PageInfo.of(searchProductResponses);

		return SearchProductPageResponse.builder()
			.searchProducts(searchProductResponses.getContent())
			.pageInfo(pageInfo)
			.build();
	}
}
