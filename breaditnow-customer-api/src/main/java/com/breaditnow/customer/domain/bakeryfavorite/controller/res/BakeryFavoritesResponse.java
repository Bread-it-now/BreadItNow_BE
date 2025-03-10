package com.breaditnow.customer.domain.bakeryfavorite.controller.res;

public record BakeryFavoritesResponse(
	Long bakeryId,
	String name,
	String profileImage,
	double distance
) {
}
