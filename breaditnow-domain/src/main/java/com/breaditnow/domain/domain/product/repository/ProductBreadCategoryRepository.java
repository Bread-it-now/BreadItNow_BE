package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.product.entity.ProductBreadCategory;

public interface ProductBreadCategoryRepository extends JpaRepository<ProductBreadCategory, Long> {
	void deleteAllByProductId(Long productId);
}
