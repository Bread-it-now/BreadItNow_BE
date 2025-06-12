package com.breaditnow.customer.bakery.presentation.request;

public record BakeryFavoriteSearchRequest(
        Integer page,
        Integer size,
        String sort,
        Double latitude,
        Double longitude
) {
}
