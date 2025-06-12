package com.breaditnow.customer.bakery.presentation.request;

public record HotBakerySearchRequest(
        Integer page,
        Integer size,
        String sort,
        String period,
        Double latitude,
        Double longitude
) {
}
