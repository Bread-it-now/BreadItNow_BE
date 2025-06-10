package com.breaditnow.customer.bakery.infrastructure;

import com.breaditnow.customer.bakery.domain.port.LoadBakeryPort;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryPort;
import com.breaditnow.customer.bakery.infrastructure.jpa.JpaBakeryRepository;
import com.breaditnow.domain.global.exception.DomainErrorCode;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_MISMATCH;
import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BakeryAdapter implements SaveBakeryPort, LoadBakeryPort {
    private final JpaBakeryRepository jpaBakeryRepository;

    @Override
    public void increaseFavoriteCount(Long bakeryId) {
        jpaBakeryRepository.increaseFavoriteCount(bakeryId);
    }

    @Override
    public void decreaseFavoriteCount(Long bakeryId) {
        jpaBakeryRepository.decreaseFavoriteCount(bakeryId);
    }

    public void validateIsExistBakery(Long bakeryId) {
        boolean isExist = jpaBakeryRepository.existsById(bakeryId);
        if(!isExist) {
            throw new DomainException(BAKERY_NOT_FOUND);
        }
    }
}
