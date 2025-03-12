package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import com.breaditnow.common.util.geodistance.GeoPoint;
import com.breaditnow.domain.domain.bakery.entity.QBakery;
import com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite;
import com.querydsl.core.types.OrderSpecifier;

public interface BakeryFavoriteSortStrategy {
	OrderSpecifier<?> getOrderSpecifier(QBakery bakery, QCustomerBakeryFavorite customerBakeryFavorite);

	// 기본 초기화 메서드
	default void initialize(GeoPoint geoPoint) {
	}
}
