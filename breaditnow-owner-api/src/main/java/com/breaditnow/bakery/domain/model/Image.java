package com.breaditnow.bakery.domain.model;

public record Image(String imageUrl) {
    public static Image create(String imageUrl) {
        return new Image(imageUrl);
    }
}
