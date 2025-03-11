package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.breaditnow.domain.global.exception.DomainException;

@Component
public class BakeryFavoriteSortStrategyFactory {

	private final Map<String, BakeryFavoriteSortStrategy> strategies;

	public BakeryFavoriteSortStrategyFactory(LatestBakeryFavoriteSortStrategy latestStrategy,
		PopularBakeryFavoriteSortStrategy popularStrategy, DistanceBakeryFavoriteSortStrategy distanceStrategy) {
		this.strategies = Map.of(
			"latest", latestStrategy,
			"popular", popularStrategy,
			"distance", distanceStrategy
		);
	}

	public BakeryFavoriteSortStrategy getStrategy(Sort sort) {
		String key = sort.isSorted() ? sort.iterator().next().getProperty().toLowerCase() : "latest";

		return Optional.ofNullable(strategies.get(key))
			.orElseThrow(() -> new DomainException(BAKERY_SORT_CONDITION_NOT_FOUND));
	}
}
