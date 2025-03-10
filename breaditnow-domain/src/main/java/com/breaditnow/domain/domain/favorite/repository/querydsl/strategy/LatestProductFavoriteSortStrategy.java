package com.breaditnow.domain.domain.favorite.repository.querydsl.strategy;

import org.springframework.stereotype.Component;

import com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite;
import com.breaditnow.domain.domain.product.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;

@Component
public class LatestProductFavoriteSortStrategy implements ProductFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier(QProduct product, QCustomerProductFavorite customerProductFavorite) {
		return product.modifiedAt.desc();
	}
}
