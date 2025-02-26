package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
