package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.dto.event.BakeryUpdatedEvent;
import com.breaditnow.owner.bakery.application.port.in.UpdateOperatingStatusUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.OperatingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOperatingStatusService implements UpdateOperatingStatusUseCase {
    private final BakeryRepository bakeryRepository;
    private final PublishBakeryEventPort publishBakeryEventPort;

    @Override
    @Transactional
    public void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus) {
        Bakery bakery = bakeryRepository.getById(bakeryId);
        bakery.updateOperatingStatus(ownerId, newStatus);
        bakeryRepository.save(bakery);

        BakeryUpdatedEvent event = BakeryUpdatedEvent.from(bakery);
        publishBakeryEventPort.publishBakeryUpdatedEvent(event);
    }
}
