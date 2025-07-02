package com.breaditnow.bakery.adapter.out.persistence;

import com.breaditnow.bakery.adapter.out.persistence.entity.BakeryEntity;
import com.breaditnow.bakery.adapter.out.persistence.repository.JpaBakeryRepository;
import com.breaditnow.bakery.domain.model.Bakery;
import com.breaditnow.bakery.domain.port.out.BakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BakeryPersistenceAdapter implements BakeryRepository {
    private final JpaBakeryRepository jpaBakeryRepository;

    @Override
    public Bakery save(Bakery bakery) {
        BakeryEntity entity = BakeryEntity.from(bakery);
        return jpaBakeryRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Bakery> findById(Long bakeryId) {
        return jpaBakeryRepository.findById(bakeryId)
                .map(BakeryEntity::toDomain);
    }

    @Override
    public Optional<Bakery> findByIdWithImages(Long bakeryId) {
        return jpaBakeryRepository.findBakeryWithImagesById(bakeryId)
                .map(BakeryEntity::toDomain);
    }
}
