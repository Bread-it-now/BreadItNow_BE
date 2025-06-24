package com.breaditnow.reservation.infrastructure.adapter.out.persistence;

import com.breaditnow.reservation.domain.BakeryOperationalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BakeryOperationalInfoJpaRepository extends JpaRepository<BakeryOperationalInfo, Long> {
    Optional<BakeryOperationalInfo> findByBakeryId(Long bakeryId);

    @Transactional
    void deleteByBakeryId(Long bakeryId);
}
