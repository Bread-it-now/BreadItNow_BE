package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.product.entity.Product;

@Repository
public interface CustomerProductFavoriteRepositoryCustom {
	Page<Product> findProductFavorites(Long customerId, Pageable pageable);
}
