package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;
import static com.querydsl.core.types.Projections.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.global.dto.BakeryFavoriteDistanceDto;
import com.breaditnow.domain.global.dto.GeoDistanceExpressionProvider;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerBakeryFavoriteRepositoryImpl implements CustomerBakeryFavoriteRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<BakeryFavoriteDistanceDto> findBakeryFavorites(Long customerId, Pageable pageable, String sort,
		GeoPoint geoPoint) {
		BooleanExpression baseCondition = customerBakeryFavorite.customer.id.eq(customerId)
			.and(customerBakeryFavorite.bakery.id.eq(bakery.id))
			.and(customerBakeryFavorite.isActive.eq(true));

		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);

		JPAQuery<BakeryFavoriteDistanceDto> query = queryFactory.select(
				constructor(BakeryFavoriteDistanceDto.class, bakery, distanceExpression))
			.from(bakery)
			.leftJoin(customerBakeryFavorite).on(customerBakeryFavorite.bakery.eq(bakery))
			.where(baseCondition)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		query.orderBy(buildOrderSpecifier(sort, distanceExpression));

		Long totalCount = queryFactory.select(customerBakeryFavorite.count())
			.from(customerBakeryFavorite, bakery)
			.where(baseCondition)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private OrderSpecifier<?> buildOrderSpecifier(String sort, NumberExpression<Double> distanceExpression) {
		if ("latest".equalsIgnoreCase(sort)) {
			return customerBakeryFavorite.modifiedAt.desc();
		} else if ("popular".equalsIgnoreCase(sort)) {
			return customerBakeryFavorite.count().desc();
		} else if ("distance".equalsIgnoreCase(sort)) {
			return distanceExpression.asc();
		}
		return customerBakeryFavorite.modifiedAt.desc();
	}
}
