package com.breaditnow.owner.bakery.infrastructure.presentation.request;

public record BakeryCreateRequest(
        String name,
        String address,
        String phoneNumber,
        String openTime,
        String introduction
) {
}
