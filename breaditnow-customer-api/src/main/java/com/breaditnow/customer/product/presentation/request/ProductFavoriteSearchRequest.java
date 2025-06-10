package com.breaditnow.customer.product.presentation.request;

public record ProductFavoriteSearchRequest(
        Integer page,
        Integer size,
        String sort,
        Double latitude,
        Double longitude
) {
    public static ProductFavoriteSearchRequest of(Integer page, Integer size, String sort, Double latitude, Double longitude) {
        return new ProductFavoriteSearchRequest(page, size, sort, latitude, longitude);
    }
}
