package com.breaditnow.customer.domain.alert.controller.req;

import java.util.List;

public record CustomerDoNotDisturbUpdateRequest(
        List<String> days,
        String startTime,
        String endTime
) {
}
