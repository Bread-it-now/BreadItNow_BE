package com.breaditnow.mysql.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.mysql.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
