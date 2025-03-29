package com.breaditnow.domain.domain.bakery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.entity.Bakery;

public interface BakeryRepositoryCustom {
	Page<Bakery> searchHotBakeries(Long customerId, Pageable pageable);
}
