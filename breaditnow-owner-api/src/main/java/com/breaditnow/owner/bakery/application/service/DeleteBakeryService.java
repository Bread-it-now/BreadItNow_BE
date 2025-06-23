package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.port.in.DeleteBakeryUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.application.port.out.PublishBakeryEventPort;
import com.breaditnow.owner.bakery.domain.Bakery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteBakeryService implements DeleteBakeryUseCase {
    private final BakeryRepository bakeryRepository;
    private final PublishBakeryEventPort publishBakeryEventPort;

    @Override
    @Transactional
    public void deleteBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = bakeryRepository.getById(bakeryId);
        bakery.delete(ownerId);
        bakeryRepository.save(bakery);
        publishBakeryEventPort.publishBakeryDeleted(bakery.getBakeryId());
    }
}
