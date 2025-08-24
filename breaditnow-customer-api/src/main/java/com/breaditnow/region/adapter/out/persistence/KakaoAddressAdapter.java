package com.breaditnow.region.adapter.out.persistence;

import com.breaditnow.customer.application.dto.AddressInfo;
import com.breaditnow.customer.adapter.out.persistence.repository.AddressPort;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
@Component
public class KakaoAddressAdapter implements AddressPort {

    private final WebClient webClient;

    public KakaoAddressAdapter(WebClient.Builder webClientBuilder, @Value("${geolocation.kakao.client-id}") String kakaoApiKey) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .build();
    }

    @Override
    public Optional<AddressInfo> getAddressInfo(double longitude, double latitude) {
        try {
            JsonNode responseNode = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/v2/local/geo/coord2address.json")
                            .queryParam("x", longitude)
                            .queryParam("y", latitude)
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            return parseCoordToAddressResponse(responseNode);

        } catch (Exception e) {
            log.error("Kakao API 주소 변환 중 오류 발생: lon={}, lat={}", longitude, latitude, e);
            return Optional.empty();
        }
    }

    private Optional<AddressInfo> parseCoordToAddressResponse(JsonNode responseNode) {
        if (responseNode == null || !responseNode.has("documents") || !responseNode.get("documents").isArray()) {
            log.warn("Kakao API 응답이 유효하지 않거나 'documents' 필드가 없습니다.");
            return Optional.empty();
        }

        JsonNode documents = responseNode.get("documents");
        if (documents.isEmpty()) {
            log.warn("주어진 좌표에 해당하는 주소 정보가 없습니다.");
            return Optional.empty();
        }

        JsonNode firstDocument = documents.get(0);

        if (!firstDocument.has("address") || !firstDocument.get("address").isObject()) {
            log.warn("응답에 'address' 필드가 없거나 객체 형태가 아닙니다.");
            return Optional.empty();
        }

        JsonNode addressNode = firstDocument.get("address");

        String shortSidoName = addressNode.path("region_1depth_name").asText(null);
        String gugun = addressNode.path("region_2depth_name").asText(null);
        String dong = addressNode.path("region_3depth_name").asText(null);

        String fullSidoName = mapToFullName(shortSidoName);

        if (fullSidoName == null || gugun == null || dong == null) {
            log.warn("주소 정보(시/도, 구/군, 동)가 응답에 포함되어 있지 않습니다.");
            return Optional.empty();
        }

        return Optional.of(
                AddressInfo.builder()
                        .sidoName(fullSidoName)
                        .gugunName(gugun)
                        .dongName(dong)
                        .build()
        );
    }

    private String mapToFullName(String shortName) {
        if (shortName == null) {
            return null;
        }
        return switch (shortName) {
            case "서울" -> "서울특별시";
            case "부산" -> "부산광역시";
            case "대구" -> "대구광역시";
            case "인천" -> "인천광역시";
            case "광주" -> "광주광역시";
            case "대전" -> "대전광역시";
            case "울산" -> "울산광역시";
            case "세종" -> "세종특별자치시";
            case "경기" -> "경기도";
            case "강원" -> "강원특별자치도";
            case "충북" -> "충청북도";
            case "충남" -> "충청남도";
            case "전북" -> "전북특별자치도";
            case "전남" -> "전라남도";
            case "경북" -> "경상북도";
            case "경남" -> "경상남도";
            case "제주" -> "제주특별자치도";
            default -> shortName; // 매핑되지 않은 경우 원래 값 반환
        };
    }
}