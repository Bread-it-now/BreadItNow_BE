package com.breaditnow.domain.global.dto;

import lombok.Builder;

public record GeoPoint(
	Double latitude,
	Double longitude
) {
	public static GeoPoint of(Double latitude, Double longitude) {
		return new GeoPoint(latitude, longitude);
	}
}
