package com.breaditnow.owner.bakery.infrastructure.adapter.in.presentation.request;

public record BakeryUpdateRequest(
        String name,
        String openTime,
        String introduction
) {
}
