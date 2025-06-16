package com.breaditnow.owner.bakery.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBakeryRepository extends JpaRepository<BakeryEntity, Long> {
}
