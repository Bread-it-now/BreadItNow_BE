package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy;

import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;

@Component
public class LatestProductFavoriteSortStrategy implements ProductFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier() {
		return customerProductFavorite.modifiedAt.desc();
	}
}
