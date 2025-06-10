package com.breaditnow.customer.bakery.infrastructure;

import com.breaditnow.customer.bakery.domain.BakeryFavorite;
import com.breaditnow.customer.bakery.domain.port.LoadBakeryFavoritePort;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryFavoritePort;
import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryFavoriteEntity;
import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryFavoriteEntityId;
import com.breaditnow.customer.bakery.infrastructure.jpa.JpaBakeryFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BakeryFavoriteAdapter implements LoadBakeryFavoritePort, SaveBakeryFavoritePort {
    private final JpaBakeryFavoriteRepository jpaBakeryFavoriteRepository;


    @Override
    public void save(BakeryFavorite bakeryFavorite) {
        BakeryFavoriteEntity entity = new BakeryFavoriteEntity(bakeryFavorite);
        jpaBakeryFavoriteRepository.save(entity);
    }

    @Override
    public Optional<BakeryFavorite> findBakeryFavorite(Long customerId, Long bakeryId) {
        return jpaBakeryFavoriteRepository.findById(new BakeryFavoriteEntityId(customerId, bakeryId))
                .map(BakeryFavoriteEntity::toDomain);
    }
}
