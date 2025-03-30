package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.entity.Product;

public interface ProductRepositoryCustom {
	Page<Product> searchHotProducts(Long customerId, String sort, Pageable pageable);
}
