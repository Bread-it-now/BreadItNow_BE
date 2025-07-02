package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.domain.OperatingStatus;
import com.breaditnow.bakery.domain.port.in.UpdateOperatingStatusUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.common.support.RepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOperatingStatusService implements UpdateOperatingStatusUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    @Transactional
    public void updateOperatingStatus(Long ownerId, Long bakeryId, OperatingStatus newStatus) {
        Bakery bakery = RepositorySupport.findBakeryOrElseThrow(bakeryRepository, bakeryId);
        bakery.updateOperatingStatus(ownerId, newStatus);
        bakeryRepository.save(bakery);
    }
}
