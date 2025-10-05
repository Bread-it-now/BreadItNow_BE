package com.breaditnow.owner.adapter.out.persistence.repository;

import com.breaditnow.owner.adapter.out.persistence.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOwnerRepository extends JpaRepository<OwnerEntity, Long> {
    boolean existsByNickname(String nickname);
}
