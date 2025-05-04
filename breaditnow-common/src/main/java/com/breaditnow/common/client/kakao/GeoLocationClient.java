package com.breaditnow.common.client.kakao;

import com.breaditnow.common.client.kakao.dto.AddressCoordinateDto;
import com.breaditnow.common.client.kakao.dto.AddressNameDto;

public interface GeoLocationClient {
    AddressCoordinateDto lookupCoordinates(String address);

    AddressNameDto lookupAddress(double latitude, double longitude);
}
