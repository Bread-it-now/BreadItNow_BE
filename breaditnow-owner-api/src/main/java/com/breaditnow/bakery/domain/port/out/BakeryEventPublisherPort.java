package com.breaditnow.bakery.domain.port.out;

import com.breaditnow.bakery.application.event.BakeryCreatedEvent;
import com.breaditnow.bakery.application.event.BakeryDeletedEvent;
import com.breaditnow.bakery.application.event.BakeryUpdatedEvent;

public interface BakeryEventPublisherPort {
    void publishBakeryCreatedEvent(BakeryCreatedEvent event);
    void publishBakeryUpdatedEvent(BakeryUpdatedEvent event);
    void publishBakeryDeleteEvent(BakeryDeletedEvent event);
}
