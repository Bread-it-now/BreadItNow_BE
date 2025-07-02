package com.breaditnow.image.domain;

public record Image(String imageUrl) {
    public static Image create(String imageUrl) {
        return new Image(imageUrl);
    }
}
