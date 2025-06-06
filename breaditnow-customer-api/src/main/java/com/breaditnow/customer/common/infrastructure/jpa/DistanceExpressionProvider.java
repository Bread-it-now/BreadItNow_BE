package com.breaditnow.customer.common.infrastructure.jpa;

import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static com.querydsl.core.types.dsl.Expressions.constant;

@Component
public class DistanceExpressionProvider {
    public NumberExpression<Double> buildDistanceExpression(GeoPoint geoPoint, NumberExpression<Double> latitude, NumberExpression<Double> longitude){
        return Expressions.numberTemplate(
                Double.class,
                "cast(function('ST_Distance_Sphere', function('Point', {0}, {1}), function('Point', {2}, {3})) as double) / 1000",
                constant(geoPoint.longitude()),
                constant(geoPoint.latitude()),
                longitude,
                latitude
        );
    }
}
