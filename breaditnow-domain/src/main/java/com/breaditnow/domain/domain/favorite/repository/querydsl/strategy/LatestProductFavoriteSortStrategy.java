package com.breaditnow.domain.domain.favorite.repository.querydsl.strategy;

import com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite;
import com.breaditnow.domain.domain.product.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;

public class LatestProductFavoriteSortStrategy implements ProductFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier(QProduct product, QCustomerProductFavorite customerProductFavorite) {
		return product.modifiedAt.desc();
	}
}
