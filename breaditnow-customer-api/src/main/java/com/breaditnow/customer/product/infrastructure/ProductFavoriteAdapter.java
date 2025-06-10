package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.domain.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.domain.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductFavoriteRepository;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntity;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntityId;
import com.breaditnow.customer.product.infrastructure.jpa.QueryProductFavoriteRepository;
import com.breaditnow.customer.product.presentation.response.ProductFavoriteResponse;
import com.breaditnow.customer.product.presentation.response.ProductFavoritePageResponse;
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
