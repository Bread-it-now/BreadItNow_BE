package com.breaditnow.domain.domain.product.dto;

import com.breaditnow.domain.domain.product.entity.Product;

import lombok.Builder;

@Builder
public record ProductFavoriteDto(
	Product product,
	Boolean isFavorite
) {
}
