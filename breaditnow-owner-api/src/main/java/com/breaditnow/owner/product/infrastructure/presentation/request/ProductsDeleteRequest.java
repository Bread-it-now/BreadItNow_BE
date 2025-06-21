package com.breaditnow.owner.product.infrastructure.presentation.request;

import java.util.List;

public record ProductsDeleteRequest(
        List<Long> productIds
) {}
