package com.breaditnow.customer.domain.search.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.search.controller.req.SearchRequest;
import com.breaditnow.customer.domain.search.controller.res.SearchBakeryPageResponse;
import com.breaditnow.customer.domain.search.controller.res.SearchProductPageResponse;
import com.breaditnow.customer.domain.search.controller.res.SearchProductResponse;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;
	private final CustomerProductFavoriteRepository favoriteRepository;

	public SearchBakeryPageResponse searchBakeries(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());

		Page<BakeryDistanceDto> bakeryPage = bakeryRepository.searchBakeriesWithKeyword(customerId, pageable,
			searchRequest.sort(), searchRequest.keyword(),
			geoPoint);

		return SearchBakeryPageResponse.of(bakeryPage);
	}

	public SearchProductPageResponse searchProducts(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());

		Page<Product> productPage = productRepository.searchProductsWithKeyword(customerId, pageable,
			searchRequest.sort(), searchRequest.keyword(), geoPoint);

		Page<SearchProductResponse> searchProductResponses = productPage.map(product -> {
			boolean isFavorite = favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
			return SearchProductResponse.of(product, isFavorite);
		});

		return SearchProductPageResponse.of(searchProductResponses);
	}
}
