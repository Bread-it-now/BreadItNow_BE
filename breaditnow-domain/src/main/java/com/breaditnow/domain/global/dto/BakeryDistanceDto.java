package com.breaditnow.domain.global.dto;

import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

@Builder
public record BakeryDistanceDto(
	Long bakeryId,
	String bakeryName,
	String profileImage,
	Double distance,
	OperatingStatus operatingStatus,
	Boolean isFavorite
) {
	@QueryProjection
	public BakeryDistanceDto {
	}
}
