package com.breaditnow.product.infrastructure;

import com.breaditnow.product.domain.port.LoadProductFavoritePort;
import com.breaditnow.product.domain.port.SaveProductFavoritePort;
import com.breaditnow.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.product.domain.ProductFavorite;
import com.breaditnow.product.infrastructure.jpa.JpaProductFavoriteRepository;
import com.breaditnow.product.infrastructure.jpa.entity.ProductFavoriteEntity;
import com.breaditnow.product.infrastructure.jpa.entity.ProductFavoriteEntityId;
import com.breaditnow.product.infrastructure.jpa.query.QueryProductFavoriteRepository;
import com.breaditnow.product.presentation.response.ProductFavoriteResponse;
import com.breaditnow.product.presentation.response.ProductFavoritePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductFavoriteAdapter implements SaveProductFavoritePort, LoadProductFavoritePort {
    private final JpaProductFavoriteRepository jpaProductFavoriteRepository;
    private final QueryProductFavoriteRepository queryProductFavoriteRepository;

    @Override
    public void save(ProductFavorite productFavorite) {
        ProductFavoriteEntity entity = new ProductFavoriteEntity(productFavorite);
        jpaProductFavoriteRepository.save(entity);
    }

    @Override
    public Optional<ProductFavorite> findProductFavorite(Long customerId, Long productId) {
        return jpaProductFavoriteRepository.findById(new ProductFavoriteEntityId(customerId, productId))
                .map(ProductFavoriteEntity::toDomain);
    }

    public ProductFavoritePageResponse getFavoriteProducts(Long customerId, ProductFavoriteSearchCriteria productFavoriteSearchCriteria) {
        Page<ProductFavoriteResponse> productFavoriteDetailsResponses = queryProductFavoriteRepository.fetchProductFavorites(customerId, productFavoriteSearchCriteria);
        return ProductFavoritePageResponse.of(productFavoriteDetailsResponses);
    }
}
