package com.breaditnow.customer.bakery.application;

import com.breaditnow.customer.bakery.domain.BakeryFavorite;
import com.breaditnow.customer.bakery.domain.event.BakeryFavoriteCreatedEvent;
import com.breaditnow.customer.bakery.domain.event.BakeryFavoriteRemovedEvent;
import com.breaditnow.customer.bakery.domain.port.LoadBakeryFavoritePort;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryFavoritePort;
import com.breaditnow.customer.common.domain.DomainEventPublisher;
import com.breaditnow.domain.global.exception.DomainErrorCode;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BakeryFavoriteService {
    private final LoadBakeryFavoritePort loadBakeryFavoritePort;
    private final SaveBakeryFavoritePort saveBakeryFavoritePort;
    private final DomainEventPublisher domainEventPublisher;

    public void addFavoriteBakery(Long customerId, Long bakeryId) {
        BakeryFavorite bakeryFavorite = loadBakeryFavoritePort.findBakeryFavorite(customerId, bakeryId)
                .map(favorite -> {
                    favorite.activate();
                    return favorite;
                })
                .orElseGet(() -> BakeryFavorite.create(customerId, bakeryId));

        saveBakeryFavoritePort.save(bakeryFavorite);
        domainEventPublisher.publish(new BakeryFavoriteCreatedEvent(bakeryId));
    }

    public void removeFavoriteBakery(Long customerId, Long bakeryId) {
        BakeryFavorite bakeryFavorite = loadBakeryFavoritePort.getBakeryFavorite(customerId, bakeryId);
        bakeryFavorite.deactivate();

        saveBakeryFavoritePort.save(bakeryFavorite);
        domainEventPublisher.publish(new BakeryFavoriteRemovedEvent(bakeryId));
    }
}
