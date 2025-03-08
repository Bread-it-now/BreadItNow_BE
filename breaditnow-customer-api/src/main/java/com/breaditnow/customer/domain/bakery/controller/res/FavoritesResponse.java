package com.breaditnow.customer.domain.bakery.controller.res;

public record FavoritesResponse(
	Long bakeryId,
	String name,
	String profileImage,
	double distance
) {
}
