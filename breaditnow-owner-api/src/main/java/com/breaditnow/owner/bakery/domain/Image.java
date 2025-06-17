package com.breaditnow.owner.bakery.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Image(String imageUrl) {
    public static Image create(String imageUrl) {
        return new Image(imageUrl);
    }
}
