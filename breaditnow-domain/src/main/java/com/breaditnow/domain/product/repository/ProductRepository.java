package com.breaditnow.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
