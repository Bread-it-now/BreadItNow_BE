package com.breaditnow.customer.search.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.product.controller.res.SearchProductPageResponse;
import com.breaditnow.customer.product.controller.res.SearchProductResponse;
import com.breaditnow.customer.search.controller.req.SearchRequest;
import com.breaditnow.customer.search.controller.res.SearchAutocompleteResponse;
import com.breaditnow.customer.search.controller.res.SearchAutocompleteResponses;
import com.breaditnow.customer.search.controller.res.SearchBakeryPageResponse;
import com.breaditnow.domain.domain.bakery.repository.SearchBakeryRepository;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.SearchProductRepository;
import com.breaditnow.domain.domain.search.entity.SearchKeyword;
import com.breaditnow.domain.domain.search.repository.AutocompleteJdbcDao;
import com.breaditnow.domain.global.dto.AutoCompleteDto;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	private final AutocompleteJdbcDao autocompleteJdbcDao;
	private final SearchProductRepository searchProductRepository;
	private final SearchBakeryRepository searchBakeryRepository;
	private final CustomerProductFavoriteRepository favoriteRepository;

	public SearchBakeryPageResponse searchBakeries(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());
		SearchKeyword searchKeyword = new SearchKeyword(searchRequest.keyword());

		Page<BakeryDistanceDto> bakeryPage = searchBakeryRepository.searchBakeriesWithKeyword(customerId, pageable,
			searchRequest.sort(), searchKeyword.toBooleanModeQuery(), geoPoint);

		return SearchBakeryPageResponse.of(bakeryPage);
	}

	public SearchProductPageResponse searchProducts(Long customerId, SearchRequest searchRequest) {
		Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
		GeoPoint geoPoint = GeoPoint.of(searchRequest.latitude(), searchRequest.longitude());

		SearchKeyword searchKeyword = new SearchKeyword(searchRequest.keyword());

		Page<Product> productPage = searchProductRepository.searchProductsWithKeyword(pageable, searchRequest.sort(),
			searchKeyword.toBooleanModeQuery(), geoPoint);

		Page<SearchProductResponse> searchProductResponses = productPage.map(product -> {
			boolean isFavorite = favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
			return SearchProductResponse.of(product, isFavorite);
		});

		return SearchProductPageResponse.of(searchProductResponses);
	}

	public SearchAutocompleteResponses searchAutocomplete(String keyword) {
		SearchKeyword searchKeyword = new SearchKeyword(keyword);
		List<AutoCompleteDto> autocompletes = autocompleteJdbcDao.findByKeywordMatch(
			searchKeyword.toBooleanModeQuery());

		List<SearchAutocompleteResponse> searchAutocompleteResponseList = autocompletes.stream()
			.map(SearchAutocompleteResponse::of)
			.toList();

		return SearchAutocompleteResponses.of(searchAutocompleteResponseList);
	}
}
