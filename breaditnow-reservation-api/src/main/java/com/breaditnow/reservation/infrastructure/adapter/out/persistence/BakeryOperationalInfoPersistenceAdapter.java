package com.breaditnow.reservation.infrastructure.adapter.out.persistence;

import com.breaditnow.reservation.application.port.out.BakeryOperationalInfoRepositoryPort;
import com.breaditnow.reservation.domain.BakeryOperationalInfo;
import com.breaditnow.reservation.infrastructure.jpa.repository.BakeryOperationalInfoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BakeryOperationalInfoPersistenceAdapter implements BakeryOperationalInfoRepositoryPort {
    private final BakeryOperationalInfoJpaRepository jpaRepository;

    @Override
    public Optional<BakeryOperationalInfo> findByBakeryId(Long bakeryId) {
        return jpaRepository.findByBakeryId(bakeryId);
    }

    @Override
    public void save(BakeryOperationalInfo bakeryOperationalInfo) {
        jpaRepository.save(bakeryOperationalInfo);
    }
}
