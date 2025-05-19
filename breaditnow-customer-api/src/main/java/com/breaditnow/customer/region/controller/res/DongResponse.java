package com.breaditnow.customer.region.controller.res;

import com.breaditnow.domain.domain.region.entity.Region;
import lombok.Builder;

@Builder
public record DongResponse(
        String sidoName,
        String gugunName,
        String dongCode,
        String dongName
) {
    public static DongResponse of(Region region) {
        return DongResponse.builder()
                .sidoName(region.getSidoName())
                .gugunName(region.getGugunName())
                .dongCode(region.getId().getDongCode())
                .dongName(region.getDongName())
                .build();
    }
}