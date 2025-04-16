package com.breaditnow.customer.domain.search.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.product.controller.res.SearchProductPageResponse;
import com.breaditnow.customer.domain.product.controller.res.SearchProductResponse;
import com.breaditnow.customer.domain.search.controller.req.SearchRequest;
import com.breaditnow.customer.domain.search.controller.res.SearchAutoCompleteResponse;
import com.breaditnow.customer.domain.search.controller.res.SearchBakeryPageResponse;
import com.breaditnow.domain.domain.bakery.repository.SearchBakeryRepository;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.SearchProductRepository;
import com.breaditnow.domain.domain.search.AutoCompleteRepository;
import com.breaditnow.domain.domain.search.entity.AutoComplete;
import com.breaditnow.domain.domain.search.entity.SearchKeyword;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	private final AutoCompleteRepository autoCompleteRepository;
	private final SearchProductRepository searchProductRepository;
	private final SearchBakeryRepository searchBakeryRepository;
	private final CustomerProductFavoriteRepository favoriteRepository;

	public SearchBakeryPageResponse searchBakeries(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());

		Page<BakeryDistanceDto> bakeryPage = searchBakeryRepository.searchBakeriesWithKeyword(customerId, pageable,
			searchRequest.sort(), searchRequest.keyword(), geoPoint);

		return SearchBakeryPageResponse.of(bakeryPage);
	}

	public SearchProductPageResponse searchProducts(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());

		Page<Product> productPage = searchProductRepository.searchProductsWithKeyword(pageable, searchRequest.sort(),
			searchRequest.keyword(), geoPoint);

		Page<SearchProductResponse> searchProductResponses = productPage.map(product -> {
			boolean isFavorite = favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
			return SearchProductResponse.of(product, isFavorite);
		});

		return SearchProductPageResponse.of(searchProductResponses);
	}

	@Transactional
	public List<SearchAutoCompleteResponse> searchAutoComplete(String keyword) {
		SearchKeyword searchKeyword = new SearchKeyword(keyword);
		List<AutoComplete> autoCompletes = autoCompleteRepository.findByKeywordMatch(
			searchKeyword.toBooleanModeQuery());

		autoCompleteRepository.incrementSearchCount(searchKeyword.getKeyword());

		return autoCompletes.stream()
			.map(SearchAutoCompleteResponse::of)
			.toList();
	}
}
