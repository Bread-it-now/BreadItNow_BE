package com.breaditnow.common.client.kakao.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class AddressNameDto {
    private String sidoName;
    private String gugunName;
    private String dongName;

    private static final Map<String, String> REGION_NAME_MAPPING = Map.of(
            "서울", "서울특별시"
    );

    public AddressNameDto(String sidoName, String gugunName, String dongName) {
        this.sidoName = REGION_NAME_MAPPING.getOrDefault(sidoName, sidoName);
        this.gugunName = gugunName;
        this.dongName = dongName;
    }
}


