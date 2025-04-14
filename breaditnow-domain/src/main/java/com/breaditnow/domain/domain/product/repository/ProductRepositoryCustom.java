package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.dto.GeoPoint;

public interface ProductRepositoryCustom {
	Page<Product> searchHotProductsByFavorite(Long customerId, Pageable pageable);

	Page<Product> searchHotProductsByReservation(Long customerId, Pageable pageable);

	Page<Product> searchProductsWithKeyword(Long customerId, Pageable pageable, SortType sort, String keyword,
		GeoPoint geoPoint);
}
