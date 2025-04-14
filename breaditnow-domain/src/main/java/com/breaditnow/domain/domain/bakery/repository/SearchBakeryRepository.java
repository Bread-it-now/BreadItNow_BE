package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.bakery.enumerate.SortType.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoDistanceExpressionProvider;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.breaditnow.domain.global.dto.QBakeryDistanceDto;
import com.querydsl.core.types.OrderSpecifier;
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
		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);
		BooleanExpression isFavoriteExpr = buildIsFavoriteExpression(customerId);
		BooleanExpression keywordCondition = bakery.name.containsIgnoreCase(keyword);

		JPAQuery<BakeryDistanceDto> query = queryFactory
			.select(new QBakeryDistanceDto(bakery.id, bakery.name, bakery.profileImage, distanceExpression,
				bakery.operatingStatus, isFavoriteExpr))
			.from(bakery)
			.leftJoin(customerBakeryFavorite).on(customerBakeryFavorite.bakery.eq(bakery))
			.where(baseCondition.and(keywordCondition))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(buildOrderSpecifier(sort, distanceExpression));

		Long totalCount = queryFactory.select(bakery.count())
			.from(bakery)
			.where(baseCondition.and(keywordCondition))
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private OrderSpecifier<?> buildOrderSpecifier(SortType sort, NumberExpression<Double> distanceExpression) {
		if (sort == LATEST) {
			return bakery.modifiedAt.desc();
		} else if (sort == POPULAR) {
			customerBakeryFavorite.count().desc();
		} else if (sort == DISTANCE) {
			distanceExpression.asc();
		}
		return bakery.modifiedAt.desc();
	}

	private static BooleanExpression buildIsFavoriteExpression(Long customerId) {
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
}
