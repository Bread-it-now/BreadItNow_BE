package com.breaditnow.customer.common.infrastructure.jpa;

import com.breaditnow.common.domain.GeoPoint;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import org.springframework.stereotype.Component;

import static com.querydsl.core.types.dsl.Expressions.constant;

@Component
public class DistanceExpressionProvider {
    private static final int decimalPlaces = 1; // 소수점 첫째자리까지 반환

    public record Location(
            NumberExpression<Double> latitude,
            NumberExpression<Double> longitude
    ){
        public static Location of(NumberExpression<Double> lat, NumberExpression<Double> lon) {
            return new Location(lat, lon);
        }
    }

    public NumberExpression<Double> buildDistanceExpression(GeoPoint source, Location target) {
        if (source == null || target == null) {
            return Expressions.asNumber(Expressions.nullExpression(Double.class));
        }

        return Expressions.numberTemplate(
                Double.class,
                "ROUND(cast(function('ST_Distance_Sphere', " +
                        "function('Point', {0}, {1}), " +
                        "function('Point', {2}, {3})) as double) / 1000, " + decimalPlaces + ")",
                constant(source.longitude()),
                constant(source.latitude()),
                target.longitude(),
                target.latitude()
        );
    }
}
