package com.breaditnow.customer.domain.bakery.controller.res;

import java.util.List;

import com.breaditnow.customer.domain.product.controller.res.ProductResponse;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public record BakeryDetailResponse(
	BakeryResponse bakery,
	List<BreadReleaseScheduleResponse> releaseSchedules,
	List<ProductResponse> products
) {
	public static BakeryDetailResponse of(BakeryResponse bakeryResponse, List<ProductResponse> productResponses,
		List<BreadReleaseScheduleResponse> releaseSchedules) {
		return BakeryDetailResponse.builder()
			.bakery(bakeryResponse)
			.products(productResponses)
			.releaseSchedules(releaseSchedules)
			.build();
	}
}
