package com.breaditnow.owner.bakery.application.port.out;

import com.breaditnow.owner.bakery.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryUpdatedEvent;

public interface PublishBakeryEventPort {
    void publishBakeryCreatedEvent(BakeryCreatedEvent event);
    void publishBakeryUpdatedEvent(BakeryUpdatedEvent event);
    void publishBakeryDeleteEvent(BakeryDeletedEvent event);
}
