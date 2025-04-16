package com.breaditnow.customer.domain.search.controller.res;

import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;

import lombok.Builder;

@Builder
public record SearchBakeryResponse(
	Long bakeryId,
	String bakeryName,
	String profileImage,
	Double distance,
	Boolean isFavorite,
	OperatingStatus operatingStatus
) {
	public static SearchBakeryResponse of(BakeryDistanceDto bakeryDistanceDto) {
		return SearchBakeryResponse.builder()
			.bakeryId(bakeryDistanceDto.bakeryId())
			.bakeryName(bakeryDistanceDto.bakeryName())
			.profileImage(bakeryDistanceDto.profileImage())
			.distance(bakeryDistanceDto.distance())
			.isFavorite(bakeryDistanceDto.isFavorite())
			.operatingStatus(bakeryDistanceDto.operatingStatus())
			.build();
	}
}
