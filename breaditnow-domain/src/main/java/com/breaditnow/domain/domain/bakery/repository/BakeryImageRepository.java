package com.breaditnow.domain.domain.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.bakery.entity.BakeryImage;

public interface BakeryImageRepository extends JpaRepository<BakeryImage, Long> {
}
