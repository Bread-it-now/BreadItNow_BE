package com.breaditnow.owner.bakery.application.port.out;

import com.breaditnow.owner.bakery.domain.Bakery;

public interface PublishBakeryEventPort {
    void publishBakeryUpdatedEvent(Bakery bakery);
    void publishBakeryDeleted(Long bakeryId);
}
