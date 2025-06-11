package com.breaditnow.customer.product.presentation.response;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public record ReleaseScheduleResponse(
        String releaseTime,
        List<String> scheduledProductNames
) {
    public static List<ReleaseScheduleResponse> of(List<BreadProductResponse> breadProducts) {
        if (breadProducts == null || breadProducts.isEmpty()) {
            return List.of();
        }

        return breadProducts.stream()
                .filter(bp -> bp.getReleaseTimes() != null && !bp.getReleaseTimes().isEmpty())
                .flatMap(bp -> bp.getReleaseTimes().stream()
                        .map(time -> Map.entry(time, bp.getProductName()))
                )
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        TreeMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ))
                .entrySet().stream()
                .map(e -> new ReleaseScheduleResponse(e.getKey(), e.getValue()))
                .toList();
    }
}
