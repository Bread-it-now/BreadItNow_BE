package com.breaditnow.domain.domain.favorite.repository.querydsl.strategy;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.breaditnow.domain.global.exception.DomainException;

@Component
public class ProductFavoriteSortStrategyFactory {

	private final Map<String, ProductFavoriteSortStrategy> strategyMap;

	public ProductFavoriteSortStrategyFactory(LatestProductFavoriteSortStrategy latestStrategy,
		PopularProductFavoriteSortStrategy popularStrategy) {
		this.strategyMap = Map.of(
			"latest", latestStrategy,
			"popular", popularStrategy
		);
	}

	public ProductFavoriteSortStrategy getStrategy(String sort) {
		ProductFavoriteSortStrategy strategy = strategyMap.get(sort.toLowerCase());
		if (strategy == null) {
			throw new DomainException(BREAD_SORT_CONDITION_NOT_FOUND);
		}
		return strategy;
	}
}
