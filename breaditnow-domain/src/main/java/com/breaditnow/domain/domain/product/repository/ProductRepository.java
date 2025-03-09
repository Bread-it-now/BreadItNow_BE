package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.breaditnow.domain.global.exception.DomainException;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByBakeryId(Long bakeryId);

	default Product getByTypeAndId(ProductType type, Long id) {
		return findByTypeAndId(type, id)
			.orElseThrow(() -> new DomainException(BREAD_NOT_FOUND));
	}

	Optional<Product> findByTypeAndId(ProductType type, Long id);
}
