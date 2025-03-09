package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.product.entity.ProductBreadCategory;

public interface ProductBreadCategoryRepository extends JpaRepository<ProductBreadCategory, Long> {

	@Modifying
	@Transactional
	void deleteAllByProductId(Long productId);
}
