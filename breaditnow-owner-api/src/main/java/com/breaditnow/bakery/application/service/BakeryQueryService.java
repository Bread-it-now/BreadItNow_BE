package com.breaditnow.bakery.application.service;

import com.breaditnow.bakery.domain.port.in.GetBakeryDetailsUseCase;
import com.breaditnow.bakery.domain.port.in.GetBakeryInfoUseCase;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.common.exception.OwnerErrorCode;
import com.breaditnow.common.exception.OwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryQueryService implements GetBakeryDetailsUseCase, GetBakeryInfoUseCase {
    private final BakeryRepository bakeryRepository;

    @Override
    public Bakery getBakeryDetails(Long bakeryId) {
        return bakeryRepository.findByIdWithImages(bakeryId)
                .orElseThrow(() -> new OwnerException(OwnerErrorCode.BAKERY_NOT_FOUND));
    }

    @Override
    public Optional<Bakery> findById(Long bakeryId) {
        return bakeryRepository.findById(bakeryId);
    }
}
