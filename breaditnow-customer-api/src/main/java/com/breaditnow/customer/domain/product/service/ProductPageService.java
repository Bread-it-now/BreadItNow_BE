package com.breaditnow.customer.domain.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.product.controller.res.HotProductPageResponse;
import com.breaditnow.domain.domain.product.dto.ProductFavoriteDto;
import com.breaditnow.domain.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductPageService {
	private final ProductRepository productRepository;

	public HotProductPageResponse searchHotProducts(Long customerId, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductFavoriteDto> productFavoritePage = productRepository.searchHotProducts(customerId, sort, pageable);
		return HotProductPageResponse.of(productFavoritePage);
	}
}
