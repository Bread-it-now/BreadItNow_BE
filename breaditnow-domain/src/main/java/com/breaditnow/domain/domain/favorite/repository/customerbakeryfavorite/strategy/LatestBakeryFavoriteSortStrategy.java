package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import org.springframework.stereotype.Component;

import com.breaditnow.domain.domain.bakery.entity.QBakery;
import com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite;
import com.querydsl.core.types.OrderSpecifier;

@Component
public class LatestBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier(QBakery bakery, QCustomerBakeryFavorite customerBakeryFavorite) {
		return customerBakeryFavorite.modifiedAt.desc();
	}
}
