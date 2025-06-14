package com.breaditnow.customer.region.presentation.res;

import com.breaditnow.domain.domain.region.entity.Region;
import lombok.Builder;

@Builder
public record SidoResponse(
        String sidoCode,
        String sidoName
) {
    public static SidoResponse of(Region region) {
        return SidoResponse.builder()
                .sidoCode(region.getId().getSidoCode())
                .sidoName(region.getSidoName())
                .build();
    }
}