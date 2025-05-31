package com.breaditnow.customer.alert.application.response;

import java.util.List;

public record TodayAlertResponse(
        Long bakeryId,
        String bakeryName,
        Long productId,
        String productName,
        List<String> releaseTime
) {
    public static TodayAlertResponse of(Long bakeryId, String bakeryName,
                                        Long productId, String productName,
                                        List<String> releaseTime) {
        return new TodayAlertResponse(bakeryId, bakeryName, productId, productName, releaseTime);
    }
}
