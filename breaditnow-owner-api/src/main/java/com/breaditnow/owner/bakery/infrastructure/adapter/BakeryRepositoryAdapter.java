package com.breaditnow.owner.bakery.infrastructure.adapter;

import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.domain.port.BakeryRepository;
import com.breaditnow.owner.bakery.infrastructure.persistence.jpa.BakeryEntity;
import com.breaditnow.owner.bakery.infrastructure.persistence.jpa.JpaBakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BakeryRepositoryAdapter implements BakeryRepository {
    private final JpaBakeryRepository jpaBakeryRepository;

    @Override
    public Long save(Bakery bakery) {
        BakeryEntity entity = BakeryEntity.from(bakery);
        return jpaBakeryRepository.save(entity).getId();
    }

    @Override
    public Optional<Bakery> findById(Long bakeryId) {
        return jpaBakeryRepository.findById(bakeryId)
                .map(BakeryEntity::toDomain);
    }
}
