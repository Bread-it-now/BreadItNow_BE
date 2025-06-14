package com.breaditnow.customer.region.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Region {
    private String regionCode;
    private final String sidoName;
    private final String gugunName;
    private final String dongName;
}
