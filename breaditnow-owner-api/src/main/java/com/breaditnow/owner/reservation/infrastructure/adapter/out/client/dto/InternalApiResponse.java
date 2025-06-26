package com.breaditnow.owner.reservation.infrastructure.adapter.out.client.dto;

public record InternalApiResponse<T>(
        String status,
        T data
) {}
