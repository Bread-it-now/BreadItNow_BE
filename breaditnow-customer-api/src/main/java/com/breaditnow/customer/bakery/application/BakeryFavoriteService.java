package com.breaditnow.customer.bakery.application;

import com.breaditnow.customer.bakery.domain.BakeryFavorite;
import com.breaditnow.customer.bakery.domain.event.BakeryFavoriteCreatedEvent;
import com.breaditnow.customer.bakery.domain.event.BakeryFavoriteRemovedEvent;
import com.breaditnow.customer.bakery.domain.port.LoadBakeryFavoritePort;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryFavoritePort;
import com.breaditnow.customer.common.domain.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BakeryFavoriteService {
    private final LoadBakeryFavoritePort loadBakeryFavoritePort;
    private final SaveBakeryFavoritePort saveBakeryFavoritePort;

    public void addFavoriteBakery(Long customerId, Long bakeryId) {
        BakeryFavorite bakeryFavorite = loadBakeryFavoritePort.findBakeryFavorite(customerId, bakeryId)
                .map(favorite -> {
                    favorite.activate();
                    return favorite;
                })
                .orElseGet(() -> BakeryFavorite.create(customerId, bakeryId));

        saveBakeryFavoritePort.save(bakeryFavorite);
        Events.publish(new BakeryFavoriteCreatedEvent(bakeryId));
    }

    public void removeFavoriteBakery(Long customerId, Long bakeryId) {
        BakeryFavorite bakeryFavorite = loadBakeryFavoritePort.getBakeryFavorite(customerId, bakeryId);
        bakeryFavorite.deactivate();

        saveBakeryFavoritePort.save(bakeryFavorite);
        Events.publish(new BakeryFavoriteRemovedEvent(bakeryId));
    }
}
