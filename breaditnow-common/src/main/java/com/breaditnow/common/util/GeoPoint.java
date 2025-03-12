package com.breaditnow.common.util;

import lombok.Builder;

@Builder
public record GeoPoint(

	Double latitude,

	Double longitude
) {

}
