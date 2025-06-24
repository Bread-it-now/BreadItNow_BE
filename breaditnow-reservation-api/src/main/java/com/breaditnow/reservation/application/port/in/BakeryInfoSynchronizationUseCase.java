package com.breaditnow.reservation.application.port.in;

import com.breaditnow.reservation.application.dto.event.BakeryCreatedEvent;
import com.breaditnow.reservation.application.dto.event.BakeryOperatingStatusChangedEvent;

public interface BakeryInfoSynchronizationUseCase {
    void createBakeryRecord(BakeryCreatedEvent event);
    void synchronizeStatus(BakeryOperatingStatusChangedEvent event);
}
