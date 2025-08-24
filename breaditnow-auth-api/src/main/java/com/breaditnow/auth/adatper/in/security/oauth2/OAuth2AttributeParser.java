package com.breaditnow.auth.adatper.in.security.oauth2;

import com.breaditnow.auth.domain.model.Provider;
import lombok.Builder;

import java.util.Map;
import java.util.Objects;

import static com.breaditnow.auth.domain.model.Provider.*;
import static com.breaditnow.auth.domain.model.Provider.KAKAO;

@Builder
public record OAuth2AttributeParser(
        String providerId,
        Provider provider
) {
    public static OAuth2AttributeParser of(String registrationId, Map<String, Object> attributes){
        Provider provider = valueOf(registrationId.toUpperCase());
        return switch (provider) {
            case KAKAO -> ofKakao(attributes);
            case NAVER -> ofNaver(attributes);
            case GOOGLE -> ofGoogle(attributes);
        };
    }

    private static OAuth2AttributeParser ofKakao(Map<String, Object> attributes){
        String providerId = Objects.toString(attributes.get("id"));
        return OAuth2AttributeParser.builder()
                .providerId(providerId)
                .provider(KAKAO)
                .build();
    }

    private static OAuth2AttributeParser ofNaver(Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        String providerId = Objects.toString(response.get("id"));
        return OAuth2AttributeParser.builder()
                .providerId(providerId)
                .provider(NAVER)
                .build();
    }

    private static OAuth2AttributeParser ofGoogle(Map<String, Object> attributes){
        String providerId = Objects.toString(attributes.get("sub"));
        return OAuth2AttributeParser.builder()
                .providerId(providerId)
                .provider(GOOGLE)
                .build();
    }
}
