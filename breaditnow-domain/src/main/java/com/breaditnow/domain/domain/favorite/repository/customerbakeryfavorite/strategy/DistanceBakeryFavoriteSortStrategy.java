package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.stereotype.Component;

import com.breaditnow.domain.global.exception.DomainException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DistanceBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	private NumberExpression<Double> distanceExpression;

	@Override
	public OrderSpecifier<?> getOrderSpecifier() {
		validateDistanceExpression(this.distanceExpression);
		return distanceExpression.asc();
	}

	@Override
	public void initialize(NumberExpression<Double> distanceExpression) {
		validateDistanceExpression(distanceExpression);
		this.distanceExpression = distanceExpression;
	}

	private void validateDistanceExpression(NumberExpression<Double> expression) {
		if (expression == null) {
			throw new DomainException(CURRENT_LOCATION_NOT_SET);
		}
	}
}
