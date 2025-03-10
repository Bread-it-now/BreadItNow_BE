package com.breaditnow.customer.domain.bakeryfavorite.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

import lombok.Builder;

@Builder
public record BakeryFavoriteResponse(
	Long bakeryId,
	String name,
	String profileImage,
	double distance
) {

	public static BakeryFavoriteResponse of(Bakery bakery) {
		return BakeryFavoriteResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.profileImage(bakery.getProfileImage())
			.distance(0)
			.build();
	}
}
