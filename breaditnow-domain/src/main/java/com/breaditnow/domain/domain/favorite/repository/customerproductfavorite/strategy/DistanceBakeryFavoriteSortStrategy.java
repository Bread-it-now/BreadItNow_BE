/*
package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.stereotype.Component;

import com.breaditnow.common.util.GeoPoint;
import com.breaditnow.domain.domain.favorite.repository.GeoDistanceExpressionProvider;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy.BakeryFavoriteSortStrategy;
import com.breaditnow.domain.global.exception.DomainException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DistanceBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	private GeoPoint currentGeoPoint;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public OrderSpecifier<?> getOrderSpecifier() {
		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(
			currentGeoPoint, bakery);
		return distanceExpression.asc();
	}

	@Override
	public void initialize(GeoPoint geoPoint) {
		if (geoPoint == null) {
			throw new DomainException(CURRENT_LOCATION_NOT_SET);
		}
		this.currentGeoPoint = geoPoint;
	}
}
*/
