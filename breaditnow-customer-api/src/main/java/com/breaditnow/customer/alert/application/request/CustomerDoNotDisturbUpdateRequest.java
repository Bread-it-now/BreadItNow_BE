package com.breaditnow.customer.alert.application.request;

import java.util.List;

public record CustomerDoNotDisturbUpdateRequest(
        List<String> days,
        String startTime,
        String endTime
) {
}
