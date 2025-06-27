package com.breaditnow.customer.customer.domain.model;

import java.util.Arrays;

public enum Provider {
    EMAIL, KAKAO, NAVER, GOOGLE;

    public static Provider convert(String registerationId) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.name().equalsIgnoreCase(registerationId.toUpperCase()))
                .findFirst()
                .orElse(EMAIL);
    }
}
