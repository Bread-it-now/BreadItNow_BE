package com.breaditnow.owner.adapter.out.persistence.repository;

import com.breaditnow.owner.adapter.out.persistence.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaOwnerRepository extends JpaRepository<OwnerEntity, Long> {
    Optional<OwnerEntity> findByEmail(String email);
}
