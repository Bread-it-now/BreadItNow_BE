package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.application.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.application.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductFavoriteRepository;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntity;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntityId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductFavoriteAdapter implements SaveProductFavoritePort, LoadProductFavoritePort {
    private final JpaProductFavoriteRepository jpaProductFavoriteRepository;

    @Override
    public void save(ProductFavorite productFavorite) {
        ProductFavoriteEntity productFavoriteEntity = new ProductFavoriteEntity(productFavorite);
        jpaProductFavoriteRepository.save(productFavoriteEntity);
    }

    @Override
    public Optional<ProductFavorite> loadProductFavorite(Long customerId, Long productId) {
        return jpaProductFavoriteRepository.findById(new ProductFavoriteEntityId(customerId, productId))
                .map(ProductFavoriteEntity::toDomain);
    }
}
