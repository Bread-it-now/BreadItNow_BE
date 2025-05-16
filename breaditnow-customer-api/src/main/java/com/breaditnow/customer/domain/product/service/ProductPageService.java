package com.breaditnow.customer.domain.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.product.controller.res.HotProductPageResponse;
import com.breaditnow.customer.domain.product.controller.res.HotProductResponse;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductPageService {
	private final ProductRepository productRepository;
	private final CustomerProductFavoriteRepository favoriteRepository;

	public HotProductPageResponse searchHotProducts(Long customerId, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size);
		ProductSortStrategy productSortStrategy = ProductSortStrategy.from(sort);
		Page<Product> productPage = productSortStrategy.search(customerId, pageable, productRepository);

		Page<HotProductResponse> hotProductResponses = productPage.map(product -> {
			boolean isFavorite = customerId != null && favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
			return HotProductResponse.of(product, isFavorite);
		});
		return HotProductPageResponse.of(hotProductResponses);
	}
}
