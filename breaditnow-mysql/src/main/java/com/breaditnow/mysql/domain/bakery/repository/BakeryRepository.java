package com.breaditnow.mysql.domain.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaditnow.mysql.domain.bakery.entity.Bakery;

@Repository
public interface BakeryRepository extends JpaRepository<Bakery, Long> {
}
