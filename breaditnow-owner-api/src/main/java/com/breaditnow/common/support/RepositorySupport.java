package com.breaditnow.common.support;

import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import com.breaditnow.common.exception.OwnerErrorCode;
import com.breaditnow.common.exception.OwnerException;

public class RepositorySupport {
    private RepositorySupport() {}

    public static Bakery findBakeryOrElseThrow(BakeryRepository bakeryRepository, Long bakeryId) {
        return bakeryRepository.findById(bakeryId)
                .orElseThrow(() -> new OwnerException(OwnerErrorCode.BAKERY_NOT_FOUND));
    }
}
