package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.port.in.DeleteBakeryUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.common.support.RepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteBakeryService implements DeleteBakeryUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    @Transactional
    public void deleteBakery(Long ownerId, Long bakeryId) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);
        bakery.delete(ownerId);
        bakeryRepository.save(bakery);
    }
}
