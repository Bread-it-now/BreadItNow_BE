package com.breaditnow.customer.product.infrastructure;

import com.breaditnow.customer.product.application.port.LoadProductPort;
import com.breaditnow.customer.product.application.port.SaveProductPort;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.infrastructure.jpa.JpaProductRepository;
import com.breaditnow.customer.product.infrastructure.jpa.ProductEntity;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@Repository
@RequiredArgsConstructor
public class ProductAdapter implements LoadProductPort, SaveProductPort {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Optional<Product> loadProduct(Long productId) {
        return jpaProductRepository.findById(productId)
                .map(ProductEntity::toDomain);
    }

    @Override
    public Optional<Product> loadProductWithLock(Long productId) {
        return jpaProductRepository.loadProductWithLock(productId)
                .map(ProductEntity::toDomain);
    }


    @Override
    public void save(Product product) {
        jpaProductRepository.save(ProductEntity.from(product));
    }
}
