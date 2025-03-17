package com.breaditnow.domain.domain.favorite.repository;

import static com.querydsl.core.types.dsl.Expressions.*;

import org.springframework.stereotype.Component;

import com.breaditnow.domain.domain.bakery.entity.QBakery;
import com.breaditnow.domain.domain.vo.GeoPoint;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;

@Component
public class GeoDistanceExpressionProvider {
	public NumberExpression<Double> buildDistanceExpression(GeoPoint geoPoint, QBakery bakery) {
		if (geoPoint == null) {
			return Expressions.asNumber(Expressions.nullExpression(Double.class));
		}
		return Expressions.numberTemplate(
			Double.class,
			"cast(function('ST_Distance_Sphere', function('Point', {0}, {1}), function('Point', {2}, {3})) as double) / 1000",
			constant(geoPoint.longitude()),
			constant(geoPoint.latitude()),
			bakery.address.longitude,
			bakery.address.latitude
		);
	}
}
