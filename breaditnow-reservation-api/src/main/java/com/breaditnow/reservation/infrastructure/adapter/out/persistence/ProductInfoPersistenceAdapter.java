package com.breaditnow.reservation.infrastructure.adapter.out.persistence;

import com.breaditnow.reservation.application.port.out.ProductInfoRepositoryPort;
import com.breaditnow.reservation.domain.ProductInfo;
import com.breaditnow.reservation.infrastructure.jpa.repository.ProductInfoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductInfoPersistenceAdapter implements ProductInfoRepositoryPort {

    private final ProductInfoJpaRepository jpaRepository;

    @Override
    public Optional<ProductInfo> findById(Long productId) {
        return jpaRepository.findById(productId);
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<Long> productIds) {
        return jpaRepository.findByProductIdIn(productIds);
    }

    @Override
    public void save(ProductInfo productInfo) {
        jpaRepository.save(productInfo);
    }

    @Override
    public void deleteById(Long productId) {
        jpaRepository.deleteById(productId);
    }
}
