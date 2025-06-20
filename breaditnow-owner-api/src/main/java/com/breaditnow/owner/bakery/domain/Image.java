package com.breaditnow.owner.bakery.domain;

import com.querydsl.core.annotations.QueryEmbeddable;
import jakarta.persistence.Embeddable;

@QueryEmbeddable
@Embeddable
public record Image(String imageUrl) {
    public static Image create(String imageUrl) {
        return new Image(imageUrl);
    }
}
