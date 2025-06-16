package com.breaditnow.customer.region.presentation.res;

public record LocationRegionResponse(String sidoCode, String gugunCode, String sidoName, String gugunName) {
    public static LocationRegionResponse of (String regionCode, String sidoName, String gugunName) {
        String sidoCode = regionCode.substring(0, 2);
        String gugunCode = regionCode.substring(2, 5);
        return new LocationRegionResponse(sidoCode, gugunCode, sidoName, gugunName);
    }
}
