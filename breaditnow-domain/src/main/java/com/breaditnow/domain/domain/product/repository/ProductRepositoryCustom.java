package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.entity.Product;

public interface ProductRepositoryCustom {
	Page<Product> searchHotProductsByFavorite(Long customerId, Pageable pageable);

	Page<Product> searchHotProductsByReservation(Long customerId, Pageable pageable);
}
