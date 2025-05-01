package com.breaditnow.common.client.kakao;

import static org.springframework.http.MediaType.*;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.breaditnow.common.client.kakao.dto.res.AddressCoordinate;
import com.breaditnow.common.client.kakao.dto.res.KakaoAddressCoordinateResponse;
import com.breaditnow.common.util.RestTemplateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient implements GeoLocationClient {

	@Value("${geolocation.kakao.client-id}")
	private String kakaoApiKey;

	@Override
	public AddressCoordinate lookupCoordinates(String address) {
		if (address == null || address.isEmpty()) {
			return null;
		}

		HttpHeaders httpHeaders = RestTemplateUtil.createHeaders(APPLICATION_JSON);
		httpHeaders.set("Authorization", "KakaoAK " + kakaoApiKey);
		httpHeaders.set("Accept", "application/json");

		URI url = KakaoApiUrlBuilder.buildSearchUri(address);
		KakaoAddressCoordinateResponse coordinateResponse = RestTemplateUtil.sendGetRequestWithUri(url, httpHeaders,
			KakaoAddressCoordinateResponse.class).getBody();

		return coordinateResponse.documents().isEmpty() ? null : coordinateResponse.documents().get(0);
	}
}


