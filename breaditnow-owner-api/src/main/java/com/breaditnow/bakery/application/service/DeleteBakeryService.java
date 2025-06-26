package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.application.event.BakeryDeletedEvent;
import com.breaditnow.bakery.domain.port.in.DeleteBakeryUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.bakery.domain.port.out.BakeryEventPublisherPort;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.support.RepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteBakeryService implements DeleteBakeryUseCase {
    private final BakeryRepository bakeryRepository;
    private final BakeryEventPublisherPort bakeryEventPublisherPort;

    @Override
    @Transactional
    public void deleteBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);
        bakery.delete(ownerId);
        bakeryRepository.save(bakery);

        BakeryDeletedEvent event = new BakeryDeletedEvent(bakery.getBakeryId());
        bakeryEventPublisherPort.publishBakeryDeleteEvent(event);
    }
}
