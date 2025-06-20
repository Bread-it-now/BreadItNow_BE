package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.bakery.domain.Image;
import jakarta.persistence.Embeddable;

@Embeddable
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
