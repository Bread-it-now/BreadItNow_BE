package com.breaditnow.product.domain;

import com.breaditnow.bakery.domain.model.Image;

public record ProductInfo(
        String name,
        String description,
        Image profileImage
) {
    public static ProductInfo create(String name, String description, Image profileImage) {
        return new ProductInfo(name, description, profileImage);
    }

    public String getProfileImageUrl() {
        return profileImage != null ? profileImage.imageUrl() : null;
    }
}
