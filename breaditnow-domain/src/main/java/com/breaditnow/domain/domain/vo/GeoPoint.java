package com.breaditnow.domain.domain.vo;

import lombok.Builder;

@Builder
public record GeoPoint(

	Double latitude,

	Double longitude
) {

}
