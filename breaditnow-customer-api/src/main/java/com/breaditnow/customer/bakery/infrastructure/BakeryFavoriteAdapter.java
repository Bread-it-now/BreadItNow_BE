package com.breaditnow.customer.bakery.infrastructure;

import com.breaditnow.customer.bakery.application.request.BakeryFavoriteSearchCriteria;
import com.breaditnow.customer.bakery.domain.BakeryFavorite;
import com.breaditnow.customer.bakery.domain.port.LoadBakeryFavoritePort;
import com.breaditnow.customer.bakery.domain.port.SaveBakeryFavoritePort;
import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryFavoriteEntity;
import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryFavoriteEntityId;
import com.breaditnow.customer.bakery.infrastructure.jpa.JpaBakeryFavoriteRepository;
import com.breaditnow.customer.bakery.presentation.response.BakeryFavoritePageResponse;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.BAKERY_FAVORITE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BakeryFavoriteAdapter implements LoadBakeryFavoritePort, SaveBakeryFavoritePort {
    private final JpaBakeryFavoriteRepository jpaBakeryFavoriteRepository;
//    private final QueryBakeryFavoriteRepository queryBakeryFavoriteRepository;
    private final BakeryAdapter bakeryAdapter;

    @Override
    public void save(BakeryFavorite bakeryFavorite) {
        BakeryFavoriteEntity entity = new BakeryFavoriteEntity(bakeryFavorite);
        jpaBakeryFavoriteRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<BakeryFavorite> findBakeryFavorite(Long customerId, Long bakeryId) {
        bakeryAdapter.validateIsExistBakery(bakeryId);
        return jpaBakeryFavoriteRepository.findById(new BakeryFavoriteEntityId(customerId, bakeryId))
                .map(BakeryFavoriteEntity::toDomain);
    }

    @Override
    @Transactional
    public BakeryFavorite getBakeryFavorite(Long customerId, Long bakeryId) {
        return findBakeryFavorite(customerId, bakeryId)
                .orElseThrow(() -> new CustomerException(BAKERY_FAVORITE_NOT_FOUND));
    }

    public BakeryFavoritePageResponse getFavoriteBakeries(Long customerId, BakeryFavoriteSearchCriteria bakeryFavoriteSearchCriteria) {
//        Page<BakeryFavoriteResponse> bakeryFavoriteResponses = queryBakeryFavoriteRepository.fetchBakeryFavorites(customerId, bakeryFavoriteSearchCriteria);
//        return BakeryFavoritePageResponse.of(bakeryFavoriteResponses);
        return null;
    }
}
