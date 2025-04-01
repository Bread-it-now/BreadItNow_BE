package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.breaditnow.domain.global.exception.DomainException;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
	default Product getByTypeAndId(ProductType type, Long id) {
		return findByTypeAndId(type, id)
			.orElseThrow(() -> new DomainException(BREAD_NOT_FOUND));
	}

	Optional<Product> findByTypeAndId(ProductType type, Long id);

	@Query("select coalesce(max(p.displayOrder), 0) from Product p where p.bakery.id = :bakeryId and p.isActive = true")
	int findMaxDisplayOrderByBakeryId(@Param("bakeryId") Long bakeryId);

	@Query("select p from Product p where p.bakery.id = :bakeryId and p.isActive = true order by p.displayOrder asc")
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
			throw new DomainException(PRODUCT_MISMATCH);
		}
		return product;
	}

	default void checkProductIsAlive(Long id) {
		getByIdAndIsActiveTrue(id);
	}
}
