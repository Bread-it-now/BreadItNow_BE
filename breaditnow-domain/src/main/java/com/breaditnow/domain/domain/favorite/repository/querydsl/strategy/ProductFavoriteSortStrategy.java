package com.breaditnow.domain.domain.favorite.repository.querydsl.strategy;

import com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite;
import com.breaditnow.domain.domain.product.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;

public interface ProductFavoriteSortStrategy {
	OrderSpecifier<?> getOrderSpecifier(QProduct product, QCustomerProductFavorite customerProductFavorite);
}
