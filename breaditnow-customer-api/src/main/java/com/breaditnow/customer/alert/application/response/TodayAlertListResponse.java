package com.breaditnow.customer.alert.application.response;

import java.util.List;

public record TodayAlertListResponse(
        List<TodayAlertResponse> alerts
) {
    public static TodayAlertListResponse of(List<TodayAlertResponse> alerts) {
        return new TodayAlertListResponse(alerts);
    }
}
