package com.breaditnow.owner.bakery.infrastructure.presentation.request;

public record BakeryUpdateRequest(
        String name,
        String openTime,
        String introduction
) {
}
