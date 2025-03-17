package com.breaditnow.common.client.kakao.dto.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoAddressCoordinateResponse(
	List<AddressCoordinate> documents
) {
}
