package com.breaditnow.domain.domain.customer.enumerate;

import java.util.Arrays;

public enum Provider {
	EMAIL, KAKAO, NAVER, GOOGLE;

	public static Provider convert(String registrationId) {
		return Arrays.stream(Provider.values())
			.filter(provider -> provider.toString().equals(registrationId.toUpperCase()))
			.findFirst()
			.orElse(EMAIL);
	}
}
