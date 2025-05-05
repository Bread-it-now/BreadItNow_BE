package com.breaditnow.common.client.kakao;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KakaoApiUrlBuilder {
    private static final String DAPI_KAKAO_COM = "https://dapi.kakao.com";
    private static final String SEARCH_ADDRESS_PATH = "/v2/local/search/address.json";
    private static final String REVERSE_GEOCODE_PATH = "/v2/local/geo/coord2address.json";

    // 주소로 좌표를 찾는 API의 URI 생성
    public static URI buildSearchUri(String address) {
        return UriComponentsBuilder.fromUriString(DAPI_KAKAO_COM)
                .path(SEARCH_ADDRESS_PATH)
                .queryParam("query", address)
                .encode(UTF_8)
                .build()
                .toUri();
    }

    // 위도, 경도로 주소를 찾는 API의 URI 생성
    public static URI buildReverseGeocodeUri(double latitude, double longitude) {
        return UriComponentsBuilder.fromUriString(DAPI_KAKAO_COM)
                .path(REVERSE_GEOCODE_PATH)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .encode(UTF_8)
                .build()
                .toUri();
    }
}
