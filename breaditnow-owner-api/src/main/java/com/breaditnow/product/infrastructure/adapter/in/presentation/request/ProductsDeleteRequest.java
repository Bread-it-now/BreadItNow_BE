package com.breaditnow.product.infrastructure.adapter.in.presentation.request;

import java.util.List;

public record ProductsDeleteRequest(
        List<Long> productIds
) {}
