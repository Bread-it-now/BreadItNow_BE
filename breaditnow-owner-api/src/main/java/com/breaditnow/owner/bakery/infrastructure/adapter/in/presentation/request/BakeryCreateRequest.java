package com.breaditnow.owner.bakery.infrastructure.adapter.in.presentation.request;

public record BakeryCreateRequest(
        String name,
        String address,
        String phoneNumber,
        String openTime,
        String introduction
) {
}
