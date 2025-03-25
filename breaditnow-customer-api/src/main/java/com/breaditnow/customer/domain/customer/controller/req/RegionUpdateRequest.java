package com.breaditnow.customer.domain.customer.controller.req;

import java.util.List;

public record RegionUpdateRequest(
        String sidoCode,
        List<String> gugunCodes
) {}