package com.breaditnow.owner.bakery.domain;

public record Image(String imageUrl) {
    public static Image create(String imageUrl) {
        return new Image(imageUrl);
    }
}
