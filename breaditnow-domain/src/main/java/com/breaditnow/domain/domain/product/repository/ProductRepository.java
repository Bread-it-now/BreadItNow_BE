package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.exception.DomainException;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.bakery.id = :bakeryId and p.isActive = true")
	List<Product> findActiveByBakeryId(@Param("bakeryId") Long bakeryId);

	default Product getById(Long id) {
		return findById(id)
			.orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
	}

	default Product getByIdAndIsActiveTrue(Long id) {
		Product product = getById(id);
		if (!product.isActive()) {
			throw new DomainException(PRODUCT_INACTIVE);
		}
		return product;
	}

	default Product getByBakeryIdAndId(Long bakeryId, Long id) {
		Product product = getByIdAndIsActiveTrue(id);
		if (!product.getBakery().getId().equals(bakeryId)) {
			throw new DomainException(BAKERY_MISMATCH);
		}
		return product;
	}

}
