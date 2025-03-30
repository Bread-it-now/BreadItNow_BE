package com.breaditnow.customer.domain.bakery.controller.req;

import com.breaditnow.domain.domain.vo.GeoPoint;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record GeoPointRequest(
	@DecimalMin(value = "-90.0", message = "위도는 -90 이상이어야 합니다.")
	@DecimalMax(value = "90.0", message = "위도는 90 이하이어야 합니다.")
	Double latitude,

	@DecimalMin(value = "-180.0", message = "경도는 -180 이상이어야 합니다.")
	@DecimalMax(value = "180.0", message = "경도는 180 이하이어야 합니다.")
	Double longitude
) {
	public GeoPoint toEntity() {
		if (latitude == null || longitude == null) {
			return null;
		}

		return new GeoPoint(latitude, longitude);
	}
}
