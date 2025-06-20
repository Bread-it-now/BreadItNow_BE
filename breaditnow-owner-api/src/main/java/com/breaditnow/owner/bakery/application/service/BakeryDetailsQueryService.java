package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.bakery.application.port.in.GetBakeryDetailsUseCase;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.domain.Bakery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryDetailsQueryService implements GetBakeryDetailsUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    public Bakery getBakeryDetails(Long bakeryId) {
        return bakeryRepository.findByIdWithImages(bakeryId)
                .orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
    }
}
