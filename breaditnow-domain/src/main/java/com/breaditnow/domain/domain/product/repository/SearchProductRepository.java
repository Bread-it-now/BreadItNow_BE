package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.bakery.enumerate.SortType.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.dto.GeoDistanceExpressionProvider;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SearchProductRepository {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	public Page<Product> searchProductsWithKeyword(Pageable pageable, SortType sort, String keyword,
		GeoPoint geoPoint) {

		BooleanExpression baseCondition = product.isActive.eq(true)
			.and(product.isHidden.eq(false))
			.and(product.name.containsIgnoreCase(keyword));

		JPAQuery<Product> query = queryFactory
			.selectFrom(product)
			.leftJoin(bakery).on(product.bakery.eq(bakery))
			.where(baseCondition);

		if (sort == POPULAR) {
			query.leftJoin(customerProductFavorite).on(customerProductFavorite.product.eq(product))
				.groupBy(product.id)
				.orderBy(customerProductFavorite.count().desc(), product.id.asc());
		} else {
			if (sort == LATEST) {
				query.orderBy(product.modifiedAt.desc());
			} else if (sort == DISTANCE) {
				NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(
					geoPoint, bakery);
				query.orderBy(distanceExpression.asc());
			} else {
				query.orderBy(product.modifiedAt.desc());
			}
		}

		Long totalCount = queryFactory.select(product.count())
			.from(product)
			.where(baseCondition)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}
}
