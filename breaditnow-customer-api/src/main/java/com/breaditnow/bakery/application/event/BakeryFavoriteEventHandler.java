package com.breaditnow.bakery.application.event;

import com.breaditnow.bakery.domain.event.BakeryFavoriteCreatedEvent;
import com.breaditnow.bakery.domain.event.BakeryFavoriteRemovedEvent;
import com.breaditnow.bakery.domain.port.SaveBakeryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BakeryFavoriteEventHandler {
    private final SaveBakeryPort saveBakeryPort;

    @EventListener
    @Transactional
    public void handleBakeryFavoriteCreated(BakeryFavoriteCreatedEvent event) {
        saveBakeryPort.increaseFavoriteCount(event.getBakeryId());
    }

    @EventListener
    @Transactional
    public void handleBakeryFavoriteRemoved(BakeryFavoriteRemovedEvent event) {
        saveBakeryPort.decreaseFavoriteCount(event.getBakeryId());
    }
}
