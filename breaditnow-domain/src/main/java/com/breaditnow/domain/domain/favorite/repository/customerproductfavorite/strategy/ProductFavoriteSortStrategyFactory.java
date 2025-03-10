package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
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

	public ProductFavoriteSortStrategy getStrategy(Sort sort) {
		String key = sort.isSorted() ? sort.iterator().next().getProperty().toLowerCase() : "latest";

		return Optional.ofNullable(strategyMap.get(key))
			.orElseThrow(() -> new DomainException(BREAD_SORT_CONDITION_NOT_FOUND));
	}
}
