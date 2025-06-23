package com.breaditnow.owner.bakery.application.dto.event;

import com.breaditnow.owner.bakery.domain.Address;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.domain.OperatingStatus;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public record BakeryInfoUpdatedEvent(
        Long bakeryId,
        String name,
        String fullAddress,
        String profileImageUrl,
        List<String> additionalImageUrls,
        OperatingStatus operatingStatus,
        boolean isDeleted
) {
    public static BakeryInfoUpdatedEvent from(Bakery bakery) {
        String profileUrl = Optional.ofNullable(bakery.getProfileImage())
                .map(Image::imageUrl)
                .orElse(null);

        List<String> additionalUrls = Optional.ofNullable(bakery.getAdditionalImages())
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(Image::imageUrl)
                .collect(Collectors.toList());

        String address = Optional.ofNullable(bakery.getAddress())
                .map(Address::fullAddress)
                .orElse(null);

        return new BakeryInfoUpdatedEvent(
                bakery.getBakeryId(),
                bakery.getName(),
                address,
                profileUrl,
                additionalUrls,
                bakery.getOperatingStatus(),
                bakery.isDeleted()
        );
    }
}
