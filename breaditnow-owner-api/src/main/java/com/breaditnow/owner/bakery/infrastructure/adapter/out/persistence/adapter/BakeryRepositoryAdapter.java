package com.breaditnow.owner.bakery.infrastructure.adapter.out.persistence.adapter;

import com.breaditnow.owner.bakery.domain.Bakery;
import com.breaditnow.owner.bakery.application.port.out.BakeryRepository;
import com.breaditnow.owner.bakery.infrastructure.adapter.out.persistence.jpa.BakeryEntity;
import com.breaditnow.owner.bakery.infrastructure.adapter.out.persistence.jpa.JpaBakeryRepository;
import com.breaditnow.owner.common.exception.OwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.breaditnow.owner.common.exception.OwnerErrorCode.BAKERY_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class BakeryRepositoryAdapter implements BakeryRepository {
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
    public Bakery getById(Long bakeryId) {
        return findById(bakeryId).orElseThrow(() -> new OwnerException(BAKERY_NOT_FOUND));
    }

    @Override
    public Optional<Bakery> findByIdWithImages(Long bakeryId) {
        return jpaBakeryRepository.findBakeryWithImagesById(bakeryId)
                .map(BakeryEntity::toDomain);
    }
}
