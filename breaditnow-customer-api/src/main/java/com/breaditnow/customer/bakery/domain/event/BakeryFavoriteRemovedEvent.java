package com.breaditnow.customer.bakery.domain.event;

import lombok.Getter;

@Getter
public class BakeryFavoriteRemovedEvent {
    private final Long bakeryId;

    public BakeryFavoriteRemovedEvent(Long bakeryId) {
        this.bakeryId = bakeryId;
    }
}
