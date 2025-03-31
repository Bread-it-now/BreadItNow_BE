package com.breaditnow.domain.global.dto;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

public record BakeryFavoriteDistanceDto(
	Bakery bakery,
	Double distance
) {
}
