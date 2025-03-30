package com.breaditnow.domain.domain.bakery.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservation.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservationProduct.*;
import static com.querydsl.core.types.Projections.*;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.dto.BakeryDistanceDto;
import com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference;
import com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.GeoDistanceExpressionProvider;
import com.breaditnow.domain.domain.vo.GeoPoint;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;
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
				constructor(BakeryDistanceDto.class, bakery, distanceExpression))
			.from(bakery)
			.where(bakery.isActive.eq(true))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		applyInterestAreaCondition(query, customerId);

		OrderSpecifier<?> orderSpecifier = buildOrderSpecifier(sort, query);
		query.orderBy(orderSpecifier);

		JPAQuery<Long> countQuery = queryFactory.select(bakery.id.countDistinct())
			.from(bakery)
			.where(bakery.isActive.eq(true));
		applyInterestAreaCondition(countQuery, customerId);
		Long totalCount = countQuery.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private void applyInterestAreaCondition(JPAQuery<?> query, Long customerId) {
		if (customerId != null) {
			QCustomerRegionPreference preference = customerRegionPreference;
			query.leftJoin(preference)
				.on(preference.customer.id.eq(customerId));

			query.where(preference.isNull().or(
				preference.region.id.sidoCode.eq(bakery.address.sidoCode)
					.and(preference.region.id.gugunCode.eq(bakery.address.gugunCode))
			));
		}
	}

	private OrderSpecifier<?> buildOrderSpecifier(String sort, JPAQuery<BakeryDistanceDto> query) {
		if ("like".equalsIgnoreCase(sort)) {
			QCustomerBakeryFavorite favorite = customerBakeryFavorite;
			query.leftJoin(favorite).on(favorite.bakery.eq(bakery));
			query.groupBy(bakery.id);
			return favorite.count().desc();
		} else {
			query.leftJoin(reservation).on(reservation.bakery.eq(bakery));
			query.leftJoin(reservationProduct).on(reservationProduct.reservation.eq(reservation));
			query.where(reservation.createdAt.goe(LocalDateTime.now().minusMonths(1)));
			query.groupBy(bakery.id);
			return reservationProduct.count().desc();
		}
	}
}
