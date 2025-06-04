package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.application.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.application.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductFavoriteRepository;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductRepository;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntity;
import com.breaditnow.customer.product.infrastructure.jpa.ProductFavoriteEntityId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductFavoriteAdapter implements SaveProductFavoritePort, LoadProductFavoritePort {
    private final JpaProductRepository jpaProductRepository;
    private final JpaProductFavoriteRepository jpaProductFavoriteRepository;

    @Override
    @Transactional
    public void save(ProductFavorite productFavorite) {
        ProductFavoriteEntity productFavoriteEntity = new ProductFavoriteEntity(productFavorite);
        jpaProductFavoriteRepository.save(productFavoriteEntity);
        jpaProductRepository.increaseFavoriteProductCount(productFavorite.getProductId());
    }

    @Override
    @Transactional
    public void delete(ProductFavorite productFavorite) {
        ProductFavoriteEntity productFavoriteEntity = new ProductFavoriteEntity(productFavorite);
        jpaProductFavoriteRepository.save(productFavoriteEntity);
        jpaProductRepository.decreaseFavoriteProductCount(productFavorite.getProductId());
    }

    @Override
    public Optional<ProductFavorite> loadProductFavorite(Long customerId, Long productId) {
        return jpaProductFavoriteRepository.findById(new ProductFavoriteEntityId(customerId, productId))
                .map(ProductFavoriteEntity::toDomain);
    }
}
