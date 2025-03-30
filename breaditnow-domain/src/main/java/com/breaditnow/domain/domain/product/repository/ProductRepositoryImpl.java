package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;
import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservation.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservationProduct.*;
import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.exception.DomainException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Product> searchHotProducts(Long customerId, String sort, Pageable pageable) {
		BooleanExpression baseCondition = product.isActive.eq(true)
			.and(product.isHidden.eq(false))
			.and(product.type.eq(BREAD))
			.and(bakery.isActive.eq(true));

		JPAQuery<Product> query = queryFactory
			.select(product)
			.from(product)
			.leftJoin(product.bakery, bakery)
			.leftJoin(reservation).on(reservation.bakery.eq(bakery))
			.leftJoin(reservationProduct).on(reservationProduct.reservation.eq(reservation))
			.leftJoin(customerProductFavorite).on(customerProductFavorite.product.eq(product)
				.and(customerProductFavorite.isActive.eq(true))
			)
			.where(baseCondition)
			.groupBy(product.id)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		applyInterestAreaCondition(query, customerId);
		query.orderBy(buildOrderSpecifier(sort));

		JPAQuery<Long> countQuery = queryFactory
			.select(product.count())
			.from(product)
			.leftJoin(product.bakery, bakery)
			.where(baseCondition);

		applyInterestAreaCondition(countQuery, customerId);
		Long totalCount = countQuery.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private OrderSpecifier<?> buildOrderSpecifier(String sort) {
		if ("favorite".equalsIgnoreCase(sort)) {
			return customerProductFavorite.count().desc();
		} else if ("reservation".equalsIgnoreCase(sort)) {
			return reservationProduct.count().desc();
		}
		throw new DomainException(PRODUCT_SORT_CONDITION_NOT_FOUND);
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
}
