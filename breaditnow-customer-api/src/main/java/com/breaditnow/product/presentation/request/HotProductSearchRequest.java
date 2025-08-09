package com.breaditnow.product.presentation.request;

public record HotProductSearchRequest(
        Integer page,
        Integer size,
        String sort,
        String period,
        Double latitude,
        Double longitude
) {
}
