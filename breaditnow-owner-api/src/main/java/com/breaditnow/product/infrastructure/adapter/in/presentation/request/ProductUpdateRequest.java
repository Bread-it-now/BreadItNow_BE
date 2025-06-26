package com.breaditnow.product.infrastructure.adapter.in.presentation.request;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.product.domain.ProductType;

import java.util.List;
import java.util.Optional;

public record ProductUpdateRequest(
        ProductType productType,
        String name,
        Integer price,
        String description,
        List<String> releaseTimes
) {
    public List<DailyTime> toDailyTimes() {
        return Optional.ofNullable(releaseTimes())
                .orElse(List.of())
                .stream()
                .map(DailyTime::of)
                .toList();
    }
}
