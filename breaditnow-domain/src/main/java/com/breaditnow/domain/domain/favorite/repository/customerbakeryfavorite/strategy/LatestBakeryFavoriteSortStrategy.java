package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;

@Component
public class LatestBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier() {
		return customerBakeryFavorite.modifiedAt.desc();
	}
}
