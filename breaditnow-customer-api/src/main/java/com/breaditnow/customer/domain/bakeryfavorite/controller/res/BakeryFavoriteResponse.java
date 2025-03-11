package com.breaditnow.customer.domain.bakeryfavorite.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

import lombok.Builder;

@Builder
public record BakeryFavoriteResponse(
	Long bakeryId,
	String name,
	String profileImage,
	Double distance
) {

	public static BakeryFavoriteResponse of(Bakery bakery, Double distance) {
		return BakeryFavoriteResponse.builder()
			.bakeryId(bakery.getId())
			.name(bakery.getName())
			.profileImage(bakery.getProfileImage())
			.distance(distance)
			.build();
	}
}
