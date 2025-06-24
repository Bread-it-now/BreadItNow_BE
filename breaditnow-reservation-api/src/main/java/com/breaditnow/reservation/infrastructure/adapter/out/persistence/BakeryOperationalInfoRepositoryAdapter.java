package com.breaditnow.reservation.infrastructure.adapter.out.persistence;

import com.breaditnow.reservation.application.port.out.BakeryOperationalInfoRepositoryPort;
import com.breaditnow.reservation.domain.BakeryOperationalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BakeryOperationalInfoRepositoryAdapter implements BakeryOperationalInfoRepositoryPort {
    private final BakeryOperationalInfoJpaRepository jpaRepository;

    @Override
    public Optional<BakeryOperationalInfo> findByBakeryId(Long bakeryId) {
        return jpaRepository.findByBakeryId(bakeryId);
    }

    @Override
    public void save(BakeryOperationalInfo bakeryOperationalInfo) {
        jpaRepository.save(bakeryOperationalInfo);
    }

    @Override
    public void deleteByBakeryId(Long bakeryId) {
        jpaRepository.deleteByBakeryId(bakeryId);
    }
}
