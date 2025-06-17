package com.breaditnow.owner.bakery.application.service;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.bakery.application.port.in.queries.GetBakeryUseCase;
import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryQueryService implements GetBakeryUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    public Bakery getBakery(Long bakeryId) {
        return bakeryRepository.findById(bakeryId)
                .orElseThrow(() -> new DomainException(BAKERY_NOT_FOUND));
    }
}
