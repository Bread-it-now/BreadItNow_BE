package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;

public interface BakeryFavoriteSortStrategy {
	OrderSpecifier<?> getOrderSpecifier();

	// 기본 초기화 메서드
	default void initialize(NumberExpression<Double> expression) {
	}
}
