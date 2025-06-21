package com.breaditnow.owner.location.infrastructure.kakao;

import com.breaditnow.owner.location.application.port.out.AddressPort;
import com.breaditnow.owner.location.infrastructure.AddressInfo;
import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoAddressAdapter implements AddressPort {
    private final WebClient webClient;

    public KakaoAddressAdapter(WebClient.Builder webClientBuilder, @Value("${geolocation.kakao.client-id}") String kakaoApiKey) {
        this.webClient = webClientBuilder.baseUrl("https://dapi.kakao.com")
                            .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                            .build();
    }

    @Override
    public AddressInfo getAddressInfo(String fullAddress) {
        if(StringUtils.isEmpty(fullAddress)){
            return null;
        }

        JsonNode responseNode = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v2/local/search/address.json")
                        .queryParam("query", fullAddress)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        return parseAddressCoordinateResponse(responseNode, fullAddress);
    }

    private AddressInfo parseAddressCoordinateResponse(JsonNode responseNode, String fullAddress) {
        if (responseNode == null || !responseNode.has("documents") || !responseNode.get("documents").isArray()) {
            throw new IllegalArgumentException("Invalid or malformed Kakao API response for: " + fullAddress);
        }

        JsonNode documents = responseNode.get("documents");
        if (documents.isEmpty()) {
            throw new IllegalArgumentException("No address found for: " + fullAddress);
        }

        JsonNode firstDocument = documents.get(0);

        String bCode = null;
        double x = 0.0;
        double y = 0.0;

        if (firstDocument.has("address") && firstDocument.get("address").isObject()) {
            JsonNode addressNode = firstDocument.get("address");
            if (addressNode.has("b_code")) {
                bCode = addressNode.get("b_code").asText();
            }
        }

        if (firstDocument.has("road_address") && firstDocument.get("road_address").isObject()) {
            JsonNode roadAddressNode = firstDocument.get("road_address");
            if (roadAddressNode.has("x") && roadAddressNode.has("y")) {
                x = roadAddressNode.get("x").asDouble();
                y = roadAddressNode.get("y").asDouble();
            }
        }

        if (x == 0.0 && y == 0.0 && firstDocument.has("address") && firstDocument.get("address").isObject()) {
            JsonNode addressNode = firstDocument.get("address");
            if (addressNode.has("x") && addressNode.has("y")) {
                x = addressNode.get("x").asDouble();
                y = addressNode.get("y").asDouble();
            }
        }

        if (x == 0.0 && y == 0.0 && firstDocument.has("x") && firstDocument.has("y")) {
            x = firstDocument.get("x").asDouble();
            y = firstDocument.get("y").asDouble();
        }

        if (bCode != null && x != 0.0 && y != 0.0) {
            return new AddressInfo(bCode, y, x);
        }

        throw new IllegalArgumentException("Required address information (b_code, x, y) not found in Kakao API response for: " + fullAddress);
    }
}
