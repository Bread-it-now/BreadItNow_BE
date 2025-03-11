package com.breaditnow.common.util.geodistance;

import lombok.Builder;

@Builder
public record GeoPoint(
	double latitude,
	double longitude
) {
}
