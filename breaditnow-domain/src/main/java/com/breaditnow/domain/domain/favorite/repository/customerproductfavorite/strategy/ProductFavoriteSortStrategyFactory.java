package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.breaditnow.domain.global.exception.DomainException;
import com.querydsl.core.types.dsl.NumberExpression;

@Component
public class ProductFavoriteSortStrategyFactory {

	private final Map<String, ProductFavoriteSortStrategy> strategies;

	public ProductFavoriteSortStrategyFactory(LatestProductFavoriteSortStrategy latestStrategy,
		PopularProductFavoriteSortStrategy popularStrategy, DistanceProductFavoriteSortStrategy distanceStrategy) {
		this.strategies = Map.of(
			"latest", latestStrategy,
			"popular", popularStrategy,
			"distance", distanceStrategy
		);
	}

	public ProductFavoriteSortStrategy getStrategy(Sort sort, NumberExpression<Double> expression) {
		String key = sort.isSorted() ? sort.iterator().next().getProperty().toLowerCase() : "latest";

		ProductFavoriteSortStrategy strategy = Optional.ofNullable(strategies.get(key))
			.orElseThrow(() -> new DomainException(BREAD_SORT_CONDITION_NOT_FOUND));

		strategy.initialize(expression);

		return strategy;
	}
}
