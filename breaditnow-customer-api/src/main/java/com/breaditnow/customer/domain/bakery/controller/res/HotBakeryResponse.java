package com.breaditnow.customer.domain.bakery.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;

import lombok.Builder;

@Builder
public record HotBakeryResponse(
	Long bakeryId,
	String name,
	String profileImage,
	Double distance,
	OperatingStatus operatingStatus
) {
	public static HotBakeryResponse of(Bakery bakery) {
		return HotBakeryResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.profileImage(bakery.getProfileImage())
			.distance(0.0)
			.operatingStatus(bakery.getOperatingStatus())
			.build();
	}
}
