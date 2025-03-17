package com.breaditnow.common.client.kakao;

import com.breaditnow.common.client.kakao.dto.res.AddressCoordinate;

public interface GeoLocationClient {
	AddressCoordinate lookupCoordinates(String address);
}
