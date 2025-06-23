package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.port.in.UpdateBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.infrastructure.adapter.in.presentation.request.BakeryUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBakeryService implements UpdateBakeryUseCase {
    private final BakeryRepository bakeryRepository;
    private final PublishBakeryEventPort publishBakeryEventPort;

    @Override
    @Transactional
    public void updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request) {
        Bakery bakery = bakeryRepository.getById(bakeryId);
        bakery.update(ownerId, request.name(), request.openTime(), request.introduction());
        bakeryRepository.save(bakery);
        publishBakeryEventPort.publishBakeryUpdatedEvent(bakery);
    }
}
