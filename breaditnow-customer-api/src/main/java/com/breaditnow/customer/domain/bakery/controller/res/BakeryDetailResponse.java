package com.breaditnow.customer.domain.bakery.controller.res;

import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;

import java.util.List;

import com.breaditnow.customer.domain.product.controller.res.ProductResponse;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public record BakeryDetailResponse(
	BakeryResponse bakery,
	List<BreadReleaseScheduleResponse> releaseSchedules,
	List<ProductResponse> breadProducts,
	List<ProductResponse> otherProducts
) {
	public static BakeryDetailResponse of(BakeryResponse bakeryResponse, List<ProductResponse> productResponses,
		List<BreadReleaseScheduleResponse> releaseSchedulesResponse) {

		List<ProductResponse> breadProducts = productResponses.stream()
			.filter(product -> product.productType() == BREAD)
			.toList();

		List<ProductResponse> otherProducts = productResponses.stream()
			.filter(product -> product.productType() == BREAD)
			.toList();

		return BakeryDetailResponse.builder()
			.bakery(bakeryResponse)
			.breadProducts(breadProducts)
			.otherProducts(otherProducts)
			.releaseSchedules(releaseSchedulesResponse)
			.build();
	}
}
