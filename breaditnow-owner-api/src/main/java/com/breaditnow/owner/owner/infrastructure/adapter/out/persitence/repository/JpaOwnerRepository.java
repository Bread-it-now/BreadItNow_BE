package com.breaditnow.owner.owner.infrastructure.adapter.out.persitence.repository;

import com.breaditnow.owner.owner.infrastructure.adapter.out.persitence.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaOwnerRepository extends JpaRepository<OwnerEntity, Long> {
    Optional<OwnerEntity> findByEmail(String email);
}
