package com.breaditnow.domain.domain.bakery.dto;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

public record BakeryDistanceDto(
	Bakery bakery,
	Double distance,
	Boolean bakeryFavorite
) {
}
