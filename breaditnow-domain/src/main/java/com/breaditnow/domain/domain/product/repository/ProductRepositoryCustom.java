package com.breaditnow.domain.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.dto.ProductFavoriteDto;

public interface ProductRepositoryCustom {
	Page<ProductFavoriteDto> searchHotProducts(Long customerId, String sort, Pageable pageable);
}
