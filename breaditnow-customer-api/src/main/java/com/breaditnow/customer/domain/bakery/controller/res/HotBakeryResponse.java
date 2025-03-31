package com.breaditnow.customer.domain.bakery.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;

import lombok.Builder;

@Builder
public record HotBakeryResponse(
	Long bakeryId,
	String bakeryName,
	String profileImage,
	Double distance,
	Boolean isFavorite,
	OperatingStatus operatingStatus
) {
	public static HotBakeryResponse of(Bakery bakery, Double distance, Boolean isFavorite) {
		return HotBakeryResponse.builder()
			.bakeryId(bakery.getId())
			.bakeryName(bakery.getName())
			.profileImage(bakery.getProfileImage())
			.distance(distance)
			.isFavorite(isFavorite)
			.operatingStatus(bakery.getOperatingStatus())
			.build();
	}
}
