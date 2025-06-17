package com.breaditnow.owner.bakery.infrastructure.presentation.response;

import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.bakery.domain.OperatingStatus;

import java.util.List;
import java.util.stream.Collectors;

public record BakeryResponse(
        Long bakeryId,
        String name,
        String address,
        String phone,
        String openTime,
        String introduction,
        String profileImage,
        List<String> additionalImages,
        OperatingStatus operatingStatus
) {
    public static BakeryResponse from(Bakery bakery) {
        String profileImageUrl = bakery.getProfileImage() != null ? bakery.getProfileImage().imageUrl() : null;

        List<String> additionalImageUrls = bakery.getAdditionalImages().stream()
                .map(Image::imageUrl)
                .collect(Collectors.toList());

        return new BakeryResponse(
                bakery.getBakeryId(),
                bakery.getName(),
                bakery.getAddress().fullAddress(),
                bakery.getPhoneNumber().value(),
                bakery.getOpenTime(),
                bakery.getIntroduction(),
                profileImageUrl,
                additionalImageUrls,
                bakery.getOperatingStatus()
        );
    }
}
