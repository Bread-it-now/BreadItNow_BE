package com.breaditnow.reservation.application.port.in;

import com.breaditnow.reservation.application.dto.event.*;

public interface BakeryInfoSynchronizationUseCase {
    void createBakeryInfo(BakeryCreatedEvent event);
    void updateBakeryInfo(BakeryUpdatedEvent event);
    void deleteBakeryInfo(BakeryDeletedEvent event);
}
