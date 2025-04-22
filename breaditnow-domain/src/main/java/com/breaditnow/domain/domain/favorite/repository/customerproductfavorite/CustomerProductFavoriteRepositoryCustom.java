package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.dto.GeoPoint;

@Repository
public interface CustomerProductFavoriteRepositoryCustom {
	Page<Product> findProductFavorites(Long customerId, Pageable pageable, SortType sort, GeoPoint geoPoint);
}
