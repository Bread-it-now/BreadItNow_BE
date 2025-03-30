package com.breaditnow.domain.domain.favorite.dto;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

public record BakeryFavoriteDistanceDto(
	Bakery bakery,
	Double distance
) {
}
