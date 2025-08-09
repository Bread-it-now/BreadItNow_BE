package com.breaditnow.bakery.domain.event;

import lombok.Getter;

@Getter
public class BakeryFavoriteCreatedEvent {
    private final Long bakeryId;

    public BakeryFavoriteCreatedEvent(Long bakeryId) {
        this.bakeryId = bakeryId;
    }
}
