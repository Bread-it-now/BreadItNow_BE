/*
package com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BakeryFavoriteSortFactory {

	private final Map<String, BakeryFavoriteSortStrategy> strategyMap;

	public BakeryFavoriteSortFactory(LatestBakeryFavoriteSortStrategy latestStrategy,
		PopularBakeryFavoriteSortStrategy popularStrategy) {
		this.strategyMap = Map.of(
			"latest", latestStrategy,
			"popular", popularStrategy
		);
	}

	public BakeryFavoriteSortStrategy getStrategy(String sort) {
		BakeryFavoriteSortStrategy strategy = strategyMap.get(sort.toLowerCase());
		// if (strategy == null) {
		// 	throw new CustomerException(SORT_CONDITION_NOT_FOUND);
		// }
		return strategy;
	}
}
*/
