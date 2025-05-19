package com.breaditnow.customer.bakery.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;

import lombok.Builder;

@Builder
public record BakeryFavoriteResponse(
	Long bakeryId,
	String name,
	String profileImage,
	Double distance,
	boolean isBakeryActive,
	OperatingStatus operatingStatus
) {

	public static BakeryFavoriteResponse of(Bakery bakery, Double distance) {
		return BakeryFavoriteResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.profileImage(bakery.getProfileImage())
			.distance(distance)
			.isBakeryActive(bakery.isActive())
			.operatingStatus(bakery.getOperatingStatus())
			.build();
	}
}
