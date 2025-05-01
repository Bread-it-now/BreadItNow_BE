package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.breaditnow.domain.global.dto.QBakeryDistanceDto;
import com.breaditnow.domain.global.provider.GeoDistanceExpressionProvider;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SearchBakeryRepository {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	public Page<BakeryDistanceDto> searchBakeriesWithKeyword(Long customerId, Pageable pageable, SortType sort,
		String keyword, GeoPoint geoPoint) {

		BooleanExpression baseCondition = bakery.isActive.eq(true);
		BooleanExpression keywordCondition = fullTextMatch(keyword);

		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);
		BooleanExpression isFavoriteExpr = buildIsFavoriteExpression(customerId);

		JPAQuery<BakeryDistanceDto> query = queryFactory
			.select(new QBakeryDistanceDto(bakery.id, bakery.name, bakery.profileImage, distanceExpression,
				bakery.operatingStatus, isFavoriteExpr))
			.from(bakery)
			.leftJoin(customerBakeryFavorite).on(customerBakeryFavorite.bakery.eq(bakery))
			.where(baseCondition.and(keywordCondition))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		applySortCondition(sort, distanceExpression, query);

		Long totalCount = queryFactory.select(bakery.count())
			.from(bakery)
			.where(baseCondition.and(keywordCondition))
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private void applySortCondition(SortType sort, NumberExpression<Double> distanceExpression,
		JPAQuery<BakeryDistanceDto> query) {
		switch (sort) {
			case LATEST -> query.orderBy(bakery.modifiedAt.desc(), bakery.id.asc());
			case POPULAR -> query.groupBy(bakery.id).orderBy(customerBakeryFavorite.count().desc(), bakery.id.asc());
			case DISTANCE -> query.orderBy(distanceExpression.asc(), bakery.id.asc());
			default -> query.orderBy(bakery.modifiedAt.desc(), bakery.id.asc());
		}
	}

	private BooleanExpression buildIsFavoriteExpression(Long customerId) {
		return JPAExpressions
			.selectOne()
			.from(customerBakeryFavorite)
			.where(
				customerBakeryFavorite.customer.id.eq(customerId),
				customerBakeryFavorite.bakery.eq(bakery),
				customerBakeryFavorite.isActive.eq(true)
			)
			.exists();
	}

	private BooleanExpression fullTextMatch(String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return null;
		}

		NumberExpression<Double> score = numberTemplate(
			Double.class,
			"function('match_bm',{0},{1})",
			bakery.name,
			constant(keyword)
		);
		return score.gt(0);
	}
}
