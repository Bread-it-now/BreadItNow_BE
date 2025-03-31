package com.breaditnow.domain.global.dto;

import lombok.Builder;

@Builder
public record GeoPoint(

	Double latitude,

	Double longitude
) {

}
