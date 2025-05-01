package com.breaditnow.common.client.kakao;

import static java.nio.charset.StandardCharsets.*;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

public class KakaoApiUrlBuilder {
	private static final String URL_PROTOCOL_HTTPS = "https://";
	private static final String DAPI_KAKAO_COM = "dapi.kakao.com";

	public static URI buildSearchUri(String address) {
		return UriComponentsBuilder.fromUriString(URL_PROTOCOL_HTTPS + DAPI_KAKAO_COM)
			.path("/v2/local/search/address.json")
			.queryParam("query", address)
			.encode(UTF_8)
			.build()
			.toUri();
	}

}
