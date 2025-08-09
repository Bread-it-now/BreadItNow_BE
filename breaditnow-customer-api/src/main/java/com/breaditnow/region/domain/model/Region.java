package com.breaditnow.region.domain.model;

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
