package com.breaditnow.common.client.kakao;

import com.breaditnow.common.client.kakao.dto.AddressCoordinateDto;
import com.breaditnow.common.client.kakao.dto.AddressNameDto;
import com.breaditnow.common.client.kakao.dto.res.KakaoAddressCoordinateResponse;
import com.breaditnow.common.client.kakao.dto.res.KakaoReverseGeocodeResponse;
import com.breaditnow.common.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient implements GeoLocationClient {

    @Value("${geolocation.kakao.client-id}")
    private String kakaoApiKey;

    private <T> T sendRequest(URI url, Class<T> responseType) {
        HttpHeaders httpHeaders = RestTemplateUtil.createHeaders(APPLICATION_JSON);
        httpHeaders.set("Authorization", "KakaoAK " + kakaoApiKey);
        httpHeaders.set("Accept", "application/json");

        return RestTemplateUtil.sendGetRequestWithUri(url, httpHeaders, responseType).getBody();
    }

    @Override
    public AddressCoordinateDto lookupCoordinates(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }

        URI url = KakaoApiUrlBuilder.buildSearchUri(address);
        KakaoAddressCoordinateResponse coordinateResponse = sendRequest(url, KakaoAddressCoordinateResponse.class);
        
        return coordinateResponse.getFirstDocumentCoordinates();
    }

    @Override
    public AddressNameDto lookupAddress(double latitude, double longitude) {
        URI url = KakaoApiUrlBuilder.buildReverseGeocodeUri(latitude, longitude);
        KakaoReverseGeocodeResponse geocodeResponse = sendRequest(url, KakaoReverseGeocodeResponse.class);

        if (geocodeResponse == null || geocodeResponse.getDocuments().isEmpty()) {
            return null;
        }

        KakaoReverseGeocodeResponse.Address address = geocodeResponse.getDocuments().get(0).getAddress();

        return new AddressNameDto(address.getSidoName(), address.getGugunName(), address.getDongName());
    }
}


