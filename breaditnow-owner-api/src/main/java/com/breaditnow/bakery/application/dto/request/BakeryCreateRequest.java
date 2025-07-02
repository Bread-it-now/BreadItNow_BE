package com.breaditnow.bakery.application.dto.request;

public record BakeryCreateRequest(
        String name,
        String address,
        String phoneNumber,
        String openTime,
        String introduction
) {
}
