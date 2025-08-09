package com.breaditnow.product.presentation.request;

public record ProductFavoriteSearchRequest(
        Integer page,
        Integer size,
        String sort,
        Double latitude,
        Double longitude
) {
}
