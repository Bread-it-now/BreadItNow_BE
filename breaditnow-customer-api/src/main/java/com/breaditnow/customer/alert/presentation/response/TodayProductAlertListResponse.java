package com.breaditnow.customer.alert.presentation.response;

import java.util.List;

public record TodayProductAlertListResponse(List<TodayProductAlertResponse> alerts) {
    public static TodayProductAlertListResponse of(List<TodayProductAlertResponse> alerts) {
        return new TodayProductAlertListResponse(alerts);
    }
}
