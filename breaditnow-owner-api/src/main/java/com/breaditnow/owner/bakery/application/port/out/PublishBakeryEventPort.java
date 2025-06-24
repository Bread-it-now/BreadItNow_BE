package com.breaditnow.owner.bakery.application.port.out;

import com.breaditnow.owner.bakery.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryDeletedEvent;
import com.breaditnow.owner.bakery.application.dto.event.BakeryOperatingStatusChangedEvent;

public interface PublishBakeryEventPort {
    void publishBakeryCreatedEvent(BakeryCreatedEvent event);
    void publishOperatingStatusChangedEvent(BakeryOperatingStatusChangedEvent event);
    void publishBakeryDeleteEvent(BakeryDeletedEvent event);
}
