package com.breaditnow.customer.bakery.presentation.response;

import com.breaditnow.customer.product.presentation.response.OtherProductResponse;
import com.breaditnow.customer.product.presentation.response.BreadProductResponse;
import com.breaditnow.customer.product.presentation.response.ReleaseScheduleResponse;

import java.util.List;

public record BakeryDetailResponse(
        BakeryResponse bakeryResponse,
        List<ReleaseScheduleResponse> releaseSchedules,
        List<BreadProductResponse> breadProducts,
        List<OtherProductResponse> otherProducts
) {
    public static BakeryDetailResponse of(BakeryResponse bakeryResponse, List<BreadProductResponse> breadProducts, List<OtherProductResponse> otherProducts) {
        List<ReleaseScheduleResponse> releaseSchedules = ReleaseScheduleResponse.of(breadProducts);
        return new BakeryDetailResponse(bakeryResponse, releaseSchedules, breadProducts, otherProducts);
    }
}
