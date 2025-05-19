package com.breaditnow.customer.region.controller.res;

import com.breaditnow.domain.domain.region.entity.Region;
import lombok.Builder;

@Builder
public record GugunResponse(
        String sidoName,
        String gugunCode,
        String gugunName
) {
    public static GugunResponse of(Region region) {
        return GugunResponse.builder()
                .sidoName(region.getSidoName())
                .gugunCode(region.getId().getGugunCode())
                .gugunName(region.getGugunName())
                .build();
    }
}
