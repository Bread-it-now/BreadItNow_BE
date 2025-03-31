package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservation.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservationProduct.*;
import static com.breaditnow.domain.global.exception.DomainErrorCode.*;
import static com.querydsl.core.types.Projections.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.dto.BakeryDistanceDto;
import com.breaditnow.domain.domain.favorite.repository.GeoDistanceExpressionProvider;
import com.breaditnow.domain.domain.vo.GeoPoint;
import com.breaditnow.domain.global.exception.DomainException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BakeryRepositoryImpl implements BakeryRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<BakeryDistanceDto> searchHotBakeries(Long customerId, String sort, Pageable pageable,
		GeoPoint geoPoint) {
		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);

		JPAQuery<BakeryDistanceDto> query = queryFactory.select(
				constructor(BakeryDistanceDto.class,
					bakery,
					distanceExpression,
					new CaseBuilder()
						.when(isBakeryFavoriteExist(customerId))
						.then(true)
						.otherwise(false)
				)
			)
			.from(bakery)
			.leftJoin(reservation).on(reservation.bakery.eq(bakery))
			.leftJoin(reservationProduct).on(reservationProduct.reservation.eq(reservation))
			.leftJoin(customerBakeryFavorite).on(customerBakeryFavorite.bakery.eq(bakery)
				.and(customerBakeryFavorite.isActive.eq(true))
			)
			.where(bakery.isActive.eq(true))
			.groupBy(bakery.id)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		applyInterestAreaCondition(query, customerId);
		query.orderBy(buildOrderSpecifier(sort));

		JPAQuery<Long> countQuery = queryFactory.select(bakery.id.countDistinct())
			.from(bakery)
			.where(bakery.isActive.eq(true));
		applyInterestAreaCondition(countQuery, customerId);
		Long totalCount = countQuery.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private OrderSpecifier<?> buildOrderSpecifier(String sort) {
		if ("favorite".equalsIgnoreCase(sort)) {
			return customerBakeryFavorite.count().desc();
		} else if ("reservation".equalsIgnoreCase(sort)) {
			return reservationProduct.count().desc();
		}
		throw new DomainException(BAKERY_SORT_CONDITION_NOT_FOUND);
	}

	private void applyInterestAreaCondition(JPAQuery<?> query, Long customerId) {
		if (customerId == null) {
			return;
		}
		Long interestCount = queryFactory
			.select(customerRegionPreference.id.count())
			.from(customerRegionPreference)
			.where(customerRegionPreference.customer.id.eq(customerId)
				.and(customerRegionPreference.region.id.sidoCode.isNotNull())
				.and(customerRegionPreference.region.id.gugunCode.isNotNull()))
			.fetchOne();

		if (interestCount != null && interestCount > 0) {
			query.leftJoin(customerRegionPreference)
				.on(customerRegionPreference.customer.id.eq(customerId));
			query.where(
				customerRegionPreference.region.id.sidoCode.eq(bakery.address.sidoCode)
					.and(customerRegionPreference.region.id.gugunCode.eq(bakery.address.gugunCode))
			);
		}
	}

	private BooleanExpression isBakeryFavoriteExist(Long customerId) {
		if (customerId == null) {
			return FALSE;
		}

		return JPAExpressions.selectOne()
			.from(customerBakeryFavorite)
			.where(
				customerBakeryFavorite.bakery.eq(bakery)
					.and(customerBakeryFavorite.customer.id.eq(customerId))
					.and(customerBakeryFavorite.isActive.eq(true))
			)
			.exists();
	}
}
