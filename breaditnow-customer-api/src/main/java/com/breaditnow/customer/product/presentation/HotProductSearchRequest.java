package com.breaditnow.customer.product.presentation;

public record HotProductSearchRequest(
        Integer page,
        Integer size,
        String sort,
        String period,
        Double latitude,
        Double longitude
) {
    public static HotProductSearchRequest of(Integer page, Integer size, String sort, String period, Double latitude, Double longitude) {
        return new HotProductSearchRequest(page, size, sort, period, latitude, longitude);
    }
}
