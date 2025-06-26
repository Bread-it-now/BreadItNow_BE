package com.breaditnow.product.adapter.in.dto.request;

import java.util.List;

public record ProductsDeleteRequest(
        List<Long> productIds
) {}
