package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.bakery.enumerate.SortType.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.breaditnow.domain.global.provider.GeoDistanceExpressionProvider;
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

		BooleanExpression baseCondition = buildBaseCondition(keyword);

		JPAQuery<Product> query = queryFactory
			.selectFrom(product)
			.leftJoin(bakery).on(product.bakery.eq(bakery))
			.where(baseCondition);

		applySortCondition(sort, geoPoint, query);

		Long totalCount = queryFactory.select(product.count())
			.from(product)
			.where(baseCondition)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private BooleanExpression buildBaseCondition(String keyword) {
		BooleanExpression activeHidden = product.isActive.eq(true)
			.and(product.isHidden.eq(false));
		BooleanExpression ft = fullTextMatch(keyword);

		return ft != null ? activeHidden.and(ft) : activeHidden;
	}

	private BooleanExpression fullTextMatch(String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return null;
		}

		NumberExpression<Double> score = numberTemplate(
			Double.class,
			"function('match_bm',{0},{1})",
			product.name,
			constant(keyword)
		);
		return score.gt(0);
	}

	private void applySortCondition(SortType sort, GeoPoint geoPoint, JPAQuery<Product> query) {
		if (sort == POPULAR) {
			query.leftJoin(customerProductFavorite)
				.on(customerProductFavorite.product.eq(product))
				.groupBy(product.id)
				.orderBy(customerProductFavorite.count().desc(), product.id.asc());
		} else if (sort == LATEST) {
			query.orderBy(product.modifiedAt.desc(), product.id.asc());
		} else if (sort == DISTANCE) {
			query.orderBy(
				distanceExpressionProvider.buildDistanceExpression(geoPoint, bakery).asc(),
				product.id.asc());
		} else {
			query.orderBy(product.modifiedAt.desc(), product.id.asc());
		}
	}
}
