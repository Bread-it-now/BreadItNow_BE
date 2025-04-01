package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservation.*;
import static com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoDistanceExpressionProvider;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.breaditnow.domain.global.dto.QBakeryDistanceDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BakeryRepositoryImpl implements BakeryRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider provider;

	@Override
	public Page<BakeryDistanceDto> searchHotBakeriesByFavorite(Long customerId, Pageable pageable, GeoPoint geoPoint) {
		Long totalCount = fetchTotalCount(customerId);

		if (totalCount == null || totalCount == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		NumberExpression<Double> distanceExpression = provider.buildDistanceExpression(geoPoint, bakery);
		BooleanExpression isFavoriteExpr = buildIsFavoriteExpression(customerId);

		JPAQuery<BakeryDistanceDto> query = queryFactory
			.select(new QBakeryDistanceDto(bakery.id, bakery.name, bakery.profileImage, distanceExpression,
				bakery.operatingStatus, isFavoriteExpr))
			.from(bakery)
			.leftJoin(customerBakeryFavorite).on(
				customerBakeryFavorite.bakery.eq(bakery)
					.and(customerBakeryFavorite.isActive.eq(true))
			)
			.where(buildBaseCondition().and(buildInterestAreaCondition(customerId)))
			.groupBy(bakery.id)
			.orderBy(customerBakeryFavorite.count().desc(), bakery.id.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		return new PageImpl<>(query.fetch(), pageable, totalCount);
	}

	@Override
	public Page<BakeryDistanceDto> searchHotBakeriesByReservation(Long customerId, Pageable pageable,
		GeoPoint geoPoint) {
		Long totalCount = fetchTotalCount(customerId);
		if (totalCount == null || totalCount == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		NumberExpression<Double> distanceExpression = provider.buildDistanceExpression(geoPoint, bakery);
		BooleanExpression isFavoriteExpr = buildIsFavoriteExpression(customerId);

		JPAQuery<BakeryDistanceDto> query = queryFactory
			.select(new QBakeryDistanceDto(bakery.id, bakery.name, bakery.profileImage, distanceExpression,
				bakery.operatingStatus, isFavoriteExpr))
			.from(bakery)
			.leftJoin(product).on(
				product.bakery.eq(bakery)
					.and(product.isActive.eq(true))
					.and(product.isHidden.eq(false))
			)
			.leftJoin(reservation).on(
				reservation.bakery.eq(bakery)
					.and(reservation.status.eq(APPROVED))
					.and(reservation.createdAt.goe(LocalDateTime.now().minusMonths(1)))
			)
			.where(
				buildBaseCondition()
					.and(buildInterestAreaCondition(customerId))
			)
			.groupBy(bakery.id)
			.orderBy(reservation.id.countDistinct().desc(), bakery.id.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<BakeryDistanceDto> fetch = query.fetch();
		return new PageImpl<>(fetch, pageable, totalCount);
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

	private Long fetchTotalCount(Long customerId) {
		return queryFactory
			.select(bakery.countDistinct())
			.from(bakery)
			.where(buildBaseCondition().and(buildInterestAreaCondition(customerId)))
			.fetchOne();
	}

	private BooleanExpression buildBaseCondition() {
		return bakery.isActive.eq(true);
	}

	private BooleanExpression buildInterestAreaCondition(Long customerId) {
		if (customerId == null) {
			return null;
		}

		Long preferenceCount = queryFactory
			.select(customerRegionPreference.count())
			.from(customerRegionPreference)
			.where(
				customerRegionPreference.customer.id.eq(customerId)
					.and(customerRegionPreference.region.id.sidoCode.isNotNull())
					.and(customerRegionPreference.region.id.gugunCode.isNotNull())
			)
			.fetchOne();

		if (preferenceCount == null || preferenceCount == 0) {
			return null;
		}

		return JPAExpressions
			.selectOne()
			.from(customerRegionPreference)
			.where(
				customerRegionPreference.customer.id.eq(customerId),
				customerRegionPreference.region.id.sidoCode.eq(bakery.address.sidoCode),
				customerRegionPreference.region.id.gugunCode.eq(bakery.address.gugunCode)
			)
			.exists();
	}

}
