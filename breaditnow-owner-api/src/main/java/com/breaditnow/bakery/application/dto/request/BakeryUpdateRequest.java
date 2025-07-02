package com.breaditnow.bakery.application.dto.request;

public record BakeryUpdateRequest(
        String name,
        String openTime,
        String introduction
) {
}
