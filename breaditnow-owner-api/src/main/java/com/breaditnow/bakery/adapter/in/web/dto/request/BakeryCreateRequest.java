package com.breaditnow.bakery.adapter.in.web.dto.request;

public record BakeryCreateRequest(
        String name,
        String address,
        String phoneNumber,
        String openTime,
        String introduction
) {
}
