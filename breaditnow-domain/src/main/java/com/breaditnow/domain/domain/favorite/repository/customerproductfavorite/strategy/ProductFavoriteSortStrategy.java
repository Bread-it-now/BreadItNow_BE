package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;

public interface ProductFavoriteSortStrategy {
	OrderSpecifier<?> getOrderSpecifier();

	default void initialize(NumberExpression<Double> expression) {
	}
}
