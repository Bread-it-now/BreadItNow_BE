package com.breaditnow.domain.domain.favorite.repository.querydsl.strategy;

import com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite;
import com.breaditnow.domain.domain.product.entity.QProduct;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;

public class PopularProductFavoriteSortStrategy implements ProductFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier(QProduct product, QCustomerProductFavorite customerProductFavorite) {
		Expression<Long> favoriteCountExpr = JPAExpressions
			.select(customerProductFavorite.count())
			.from(customerProductFavorite)
			.where(customerProductFavorite.product.id.eq(product.id)
				.and(customerProductFavorite.isActive.eq(true)));

		NumberExpression<Long> favoriteCount = Expressions.numberTemplate(Long.class, "{0}", favoriteCountExpr);

		return favoriteCount.desc();
	}
}
