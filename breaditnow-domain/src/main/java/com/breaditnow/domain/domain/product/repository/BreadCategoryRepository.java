package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.product.entity.BreadCategory;

public interface BreadCategoryRepository extends JpaRepository<BreadCategory, Long> {

}
