package com.breaditnow.customer.bakery.infrastructure;

import com.breaditnow.customer.bakery.domain.port.SaveBakeryPort;
import com.breaditnow.customer.bakery.infrastructure.jpa.JpaBakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BakeryAdapter implements SaveBakeryPort {
    private final JpaBakeryRepository jpaBakeryRepository;

    @Override
    public void increaseFavoriteCount(Long bakeryId) {
        jpaBakeryRepository.increaseFavoriteCount(bakeryId);
    }

    @Override
    public void decreaseFavoriteCount(Long bakeryId) {
        jpaBakeryRepository.decreaseFavoriteCount(bakeryId);
    }
}
