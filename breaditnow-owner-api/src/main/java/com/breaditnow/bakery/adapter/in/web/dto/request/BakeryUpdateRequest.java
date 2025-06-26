package com.breaditnow.bakery.adapter.in.web.dto.request;

public record BakeryUpdateRequest(
        String name,
        String openTime,
        String introduction
) {
}
