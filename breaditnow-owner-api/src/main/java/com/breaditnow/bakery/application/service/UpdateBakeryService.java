package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.domain.port.in.UpdateBakeryUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.adapter.in.web.dto.request.BakeryUpdateRequest;
import com.breaditnow.common.support.RepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBakeryService implements UpdateBakeryUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    @Transactional
    public void updateBakery(Long ownerId, Long bakeryId, BakeryUpdateRequest request) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);
        bakery.update(ownerId, request.name(), request.openTime(), request.introduction());

        bakeryRepository.save(bakery);
    }
}
