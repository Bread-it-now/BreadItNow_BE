package com.breaditnow.owner.product.infrastructure.presentation.request;


import com.breaditnow.owner.common.domain.DailyTime;
import com.breaditnow.owner.product.domain.ProductType;

import java.util.List;
import java.util.Optional;

public record ProductCreateRequest(
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
