package com.breaditnow.product.application.dto.request;

import java.util.List;

public record ProductsDeleteRequest(
        List<Long> productIds
) {}
