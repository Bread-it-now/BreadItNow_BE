package com.breaditnow.customer.domain.customer.controller.req;

import java.util.List;

public record CustomerInitRequest(
        String nickname,
        List<Long> breadCategoryIds
) {}
