package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy;

import org.springframework.stereotype.Component;

import com.breaditnow.domain.domain.bakery.entity.QBakery;
import com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;

@Component
public class PopularBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	@Override
	public OrderSpecifier<?> getOrderSpecifier(QBakery bakery, QCustomerBakeryFavorite customerBakeryFavorite) {
		Expression<Long> favoriteCountExpr = JPAExpressions
			.select(customerBakeryFavorite.count())
			.from(customerBakeryFavorite)
			.where(customerBakeryFavorite.bakery.id.eq(bakery.id)
				.and(customerBakeryFavorite.isActive.eq(true)));

		NumberExpression<Long> favoriteCount = Expressions.numberTemplate(Long.class, "{0}", favoriteCountExpr);
		return favoriteCount.desc();
	}
}
