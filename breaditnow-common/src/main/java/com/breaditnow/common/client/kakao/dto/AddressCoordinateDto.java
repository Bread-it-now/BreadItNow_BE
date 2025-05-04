package com.breaditnow.common.client.kakao.dto;

import lombok.Getter;

@Getter
public class AddressCoordinateDto {
    private Double latitude;
    private Double longitude;

    public AddressCoordinateDto(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressCoordinateDto(String latitude, String longitude) {
        this.latitude = parseToDouble(latitude);
        this.longitude = parseToDouble(longitude);
    }

    private Double parseToDouble(String value) {
        return Double.parseDouble(value);
    }
}
