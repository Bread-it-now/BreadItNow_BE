package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import org.springframework.stereotype.Component;

import com.breaditnow.common.util.geodistance.GeoPoint;
import com.breaditnow.domain.domain.bakery.entity.QBakery;
import com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite;
import com.breaditnow.domain.global.exception.DomainException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;

@Component
public class DistanceBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	private GeoPoint currentGeoPoint;

	public void setCurrentGeoPoint(GeoPoint currentGeoPoint) {
		this.currentGeoPoint = currentGeoPoint;
	}

	@Override
	public OrderSpecifier<?> getOrderSpecifier(QBakery bakery, QCustomerBakeryFavorite customerBakeryFavorite) {
		if (currentGeoPoint == null) {
			throw new DomainException(CURRENT_LOCATION_NOT_SET);
		}

		NumberExpression<Double> distanceExpression = Expressions.numberTemplate(
			Double.class,
			"cast(function('ST_Distance_Sphere', function('Point', {0}, {1}), function('Point', {2}, {3})) as double) / 1000",
			constant(currentGeoPoint.longitude()),
			constant(currentGeoPoint.latitude()),
			bakery.address.longitude,
			bakery.address.latitude
		);

		return distanceExpression.asc();
	}
}
