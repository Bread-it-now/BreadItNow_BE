package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;
import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservationProduct.*;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Product> searchHotProductsByFavorite(Long customerId, Pageable pageable) {
		JPAQuery<Product> query = queryFactory
			.selectFrom(product)
			.leftJoin(product.bakery, bakery).fetchJoin()
			.leftJoin(customerProductFavorite).on(customerProductFavorite.product.eq(product)
				.and(customerProductFavorite.isActive.eq(true))
			)
			.where(buildBaseCondition()
				.and(buildInterestAreaCondition(customerId))
			)
			.groupBy(product.id)
			.orderBy(customerProductFavorite.count().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		Long totalCount = queryFactory
			.select(product.count())
			.from(product)
			.leftJoin(product.bakery, bakery)
			.leftJoin(customerProductFavorite)
			.on(customerProductFavorite.product.eq(product))
			.where(buildBaseCondition()
				.and(buildInterestAreaCondition(customerId))
			)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	@Override
	public Page<Product> searchHotProductsByReservation(Long customerId, Pageable pageable) {
		JPAQuery<Product> query = queryFactory
			.selectFrom(product)
			.leftJoin(product.bakery, bakery).fetchJoin()
			.leftJoin(reservationProduct).on(reservationProduct.product.eq(product))
			.where(buildBaseCondition()
				.and(buildInterestAreaCondition(customerId))
			)
			.groupBy(product.id)
			.orderBy(reservationProduct.count().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		Long totalCount = queryFactory
			.select(product.count())
			.from(product)
			.leftJoin(product.bakery, bakery)
			.leftJoin(reservationProduct).on(reservationProduct.product.eq(product))
			.where(buildBaseCondition()
				.and(buildInterestAreaCondition(customerId))
			).fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private BooleanExpression buildBaseCondition() {
		return product.isActive.eq(true)
			.and(product.isHidden.eq(false))
			.and(product.type.eq(BREAD))
			.and(bakery.isActive.eq(true));
	}

	private BooleanExpression buildInterestAreaCondition(Long customerId) {
		if (customerId == null) {
			return null;
		}
		Long interestCount = queryFactory
			.select(customerRegionPreference.id.count())
			.from(customerRegionPreference)
			.where(
				customerRegionPreference.customer.id.eq(customerId)
					.and(customerRegionPreference.region.id.sidoCode.isNotNull())
					.and(customerRegionPreference.region.id.gugunCode.isNotNull())
			)
			.fetchOne();

		if (interestCount != null && interestCount > 0) {
			return customerRegionPreference.region.id.sidoCode.eq(bakery.address.sidoCode)
				.and(customerRegionPreference.region.id.gugunCode.eq(bakery.address.gugunCode));
		}
		return null;
	}

	private BooleanExpression recentReservationCondition() {
		return reservationProduct.createdAt.goe(LocalDateTime.now().minusMonths(1));
	}
}
