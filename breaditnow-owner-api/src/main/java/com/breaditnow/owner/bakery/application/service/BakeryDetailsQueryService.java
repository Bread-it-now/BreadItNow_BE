package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.owner.bakery.application.port.in.GetBakeryDetailsUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.common.exception.OwnerErrorCode;
import com.breaditnow.owner.common.exception.OwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryDetailsQueryService implements GetBakeryDetailsUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    public Bakery getBakeryDetails(Long bakeryId) {
        return bakeryRepository.findByIdWithImages(bakeryId)
                .orElseThrow(() -> new OwnerException(OwnerErrorCode.BAKERY_NOT_FOUND));
    }
}
