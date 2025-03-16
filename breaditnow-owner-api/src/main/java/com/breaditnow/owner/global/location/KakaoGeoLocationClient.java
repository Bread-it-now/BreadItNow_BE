package com.breaditnow.owner.global.location;

import static org.springframework.http.HttpMethod.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoGeoLocationClient implements GeoLocationClient {

	@Value("${geolocation.kakao.client-id}")
	private String kakaoApiKey;
	private static final String API_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";
	private final RestTemplate restTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public AddressCoordinate lookupCoordinates(String address) {
		if (StringUtils.isNullOrEmpty(address)) {
			return null;
		}

		String url = API_URL + address;
		HttpEntity<String> entity = new HttpEntity<>(createHeaders());
		String response = restTemplate.exchange(url, GET, entity, String.class).getBody();

		try {
			JsonNode root = objectMapper.readTree(response);
			JsonNode documents = root.path("documents");

			if (documents.isArray() && !documents.isEmpty()) {
				JsonNode firstDoc = documents.get(0);
				Double longitude = Double.valueOf(firstDoc.path("x").asText());
				Double latitude = Double.valueOf(firstDoc.path("y").asText());

				return new AddressCoordinate(latitude, longitude);
			} else {
				log.warn("Kakao API 응답에 documents 배열이 비어 있습니다. address: {}", address);
				return null;
			}
		} catch (Exception e) {
			log.error("lookupCoordinates 처리 중 예외 발생", e);
			return null;
		}
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + kakaoApiKey);
		headers.set("Accept", "application/json");
		return headers;
	}
}
