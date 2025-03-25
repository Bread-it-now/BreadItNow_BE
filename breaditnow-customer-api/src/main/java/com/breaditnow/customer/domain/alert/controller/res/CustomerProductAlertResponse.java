package com.breaditnow.customer.domain.alert.controller.res;

import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import java.util.Arrays;
import java.util.List;

public record CustomerProductAlertResponse(
        Long alertId,
        Long productId,
        String productName,
        String productImage,
        List<String> releaseTime,
        Long bakeryId,
        String bakeryName,
        boolean alertActive
) {
    public static CustomerProductAlertResponse of(CustomerProductAlert alert) {
        return new CustomerProductAlertResponse(
                alert.getId(),
                alert.getProduct().getId(),
                alert.getProduct().getName(),
                alert.getProduct().getImage() != null ? alert.getProduct().getImage() : "default.png",
                parseReleaseTimes(alert.getProduct().getReleaseTime()),
                alert.getProduct().getBakery().getId(),
                alert.getProduct().getBakery().getName(),
                alert.isActive()
        );
    }

    private static List<String> parseReleaseTimes(String releaseTimeStr) {
        if (releaseTimeStr == null || releaseTimeStr.isBlank()) {
            return List.of();
        }
        return Arrays.asList(releaseTimeStr.split(";", -1));
    }
}