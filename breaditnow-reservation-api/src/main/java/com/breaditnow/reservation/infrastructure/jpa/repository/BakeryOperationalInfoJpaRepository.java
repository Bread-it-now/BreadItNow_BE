package com.breaditnow.reservation.infrastructure.jpa.repository;

import com.breaditnow.reservation.domain.BakeryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BakeryOperationalInfoJpaRepository extends JpaRepository<BakeryInfo, Long> {
    Optional<BakeryInfo> findByBakeryId(Long bakeryId);

    @Transactional
    void deleteByBakeryId(Long bakeryId);
}
