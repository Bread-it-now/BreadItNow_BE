package com.breaditnow.domain.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.bakery.entity.Bakery;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {
}
