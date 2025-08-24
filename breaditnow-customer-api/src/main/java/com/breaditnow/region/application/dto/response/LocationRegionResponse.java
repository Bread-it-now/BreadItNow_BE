package com.breaditnow.region.application.dto.response;

import com.breaditnow.region.domain.model.Region;

public record LocationRegionResponse(String sidoCode, String gugunCode, String sidoName, String gugunName) {
    public static LocationRegionResponse of(Region region) {
        String sidoCode = region.getRegionCode().substring(0, 2);
        String gugunCode = region.getRegionCode().substring(2, 5);
        return new LocationRegionResponse(sidoCode, gugunCode, region.getSidoName(), region.getGugunName());
    }
}
