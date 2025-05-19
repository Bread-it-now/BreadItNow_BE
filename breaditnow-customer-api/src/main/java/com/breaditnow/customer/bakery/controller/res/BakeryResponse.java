package com.breaditnow.customer.bakery.controller.res;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.entity.BakeryImage;
import com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus;
import lombok.Builder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Builder
public record BakeryResponse(
        Long bakeryId,
        String name,
        String address,
        String phone,
        String openTime,
        String introduction,
        String profileImage,
        List<String> additionalImages,
        OperatingStatus operatingStatus,
        Boolean isBakeryFavorite
) {

    public static BakeryResponse of(Bakery bakery, Boolean isBakeryFavorite) {
        return BakeryResponse.builder()
                .bakeryId(bakery.getId())
                .name(bakery.getName())
                .address(bakery.getAddress().getDescription())
                .phone(bakery.getPhone())
                .openTime(bakery.getOpenTime())
                .introduction(bakery.getIntroduction())
                .profileImage(bakery.getProfileImage())
                .additionalImages(bakery.getAdditionalImages()
                        .stream()
                        .map(BakeryImage::getImageUrl)
                        .collect(toList()))
                .operatingStatus(bakery.getOperatingStatus())
                .isBakeryFavorite(isBakeryFavorite)
                .build();
    }

    public static BakeryResponse of(Bakery bakery) {
        return BakeryResponse.builder()
                .bakeryId(bakery.getId())
                .name(bakery.getName())
                .address(bakery.getAddress().getDescription())
                .phone(bakery.getPhone())
                .openTime(bakery.getOpenTime())
                .introduction(bakery.getIntroduction())
                .profileImage(bakery.getProfileImage())
                .additionalImages(bakery.getAdditionalImages()
                        .stream()
                        .map(BakeryImage::getImageUrl)
                        .collect(toList()))
                .operatingStatus(bakery.getOperatingStatus())
                .build();
    }
}
