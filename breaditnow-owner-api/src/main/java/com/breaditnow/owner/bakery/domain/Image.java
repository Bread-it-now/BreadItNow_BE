package com.breaditnow.owner.bakery.domain;

public record Image(String profileUrl) {
    public static Image create(String profileUrl) {
        return new Image(profileUrl);
    }
}
