package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.bakery.enumerate.SortType.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;
import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservationProduct.*;
import static com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.dto.GeoDistanceExpressionProvider;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<Product> searchHotProductsByFavorite(Long customerId, Pageable pageable) {
		Long totalCount = fetchTotalCount(customerId);
		if (totalCount == null || totalCount == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		List<Long> productIds = queryFactory
			.select(product.id)
			.from(product)
			.leftJoin(bakery).on(product.bakery.eq(bakery))
			.leftJoin(customerProductFavorite).on(
				customerProductFavorite.product.eq(product)
					.and(customerProductFavorite.isActive.eq(true))
			)
			.where(buildBaseCondition().and(buildInterestAreaCondition(customerId)))
			.groupBy(product.id)
			.orderBy(customerProductFavorite.count().desc(), product.id.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (productIds.isEmpty()) {
			return new PageImpl<>(Collections.emptyList(), pageable, totalCount);
		}

		List<Product> fetchedList = fetchProductsWithBakery(productIds);
		List<Product> sortedContent = sortProductsByIds(fetchedList, productIds);
		return new PageImpl<>(sortedContent, pageable, totalCount);
	}

	@Override
	public Page<Product> searchHotProductsByReservation(Long customerId, Pageable pageable) {
		Long totalCount = fetchTotalCount(customerId);
		if (totalCount == null || totalCount == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}

		JPAQuery<Long> query = queryFactory
			.select(product.id)
			.from(product)
			.leftJoin(bakery).on(product.bakery.eq(bakery))
			.leftJoin(reservationProduct).on(
				reservationProduct.product.eq(product)
					.and(reservationProduct.reservation.status.eq(APPROVED))
					.and(recentReservationCondition())
			)
			.where(buildBaseCondition().and(buildInterestAreaCondition(customerId)))
			.groupBy(product.id)
			.orderBy(reservationProduct.id.count().desc(), product.id.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		List<Long> productIds = query.fetch();

		if (productIds.isEmpty()) {
			return new PageImpl<>(Collections.emptyList(), pageable, totalCount);
		}

		List<Product> fetchedList = fetchProductsWithBakery(productIds);
		List<Product> sortedContent = sortProductsByIds(fetchedList, productIds);
		return new PageImpl<>(sortedContent, pageable, totalCount);
	}

	@Override
	public Page<Product> searchProductsWithKeyword(Long customerId, Pageable pageable, SortType sort, String keyword,
		GeoPoint geoPoint) {
		BooleanExpression baseCondition = product.isActive.eq(true)
			.and(bakery.name.containsIgnoreCase(keyword));
		
		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);

		JPAQuery<Product> query = queryFactory
			.selectFrom(product)
			.leftJoin(customerProductFavorite).on(customerProductFavorite.product.eq(product))
			.where(baseCondition)
			.groupBy(product.id)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(buildOrderSpecifier(sort, distanceExpression));

		Long totalCount = queryFactory.select(product.count())
			.from(product)
			.where(baseCondition)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private Long fetchTotalCount(Long customerId) {
		return queryFactory
			.select(product.count())
			.from(product)
			.where(buildBaseCondition().and(buildInterestAreaCondition(customerId)))
			.fetchOne();
	}

	private List<Product> fetchProductsWithBakery(List<Long> productIds) {
		return queryFactory
			.selectFrom(product)
			.leftJoin(product.bakery, bakery).fetchJoin()
			.where(product.id.in(productIds))
			.fetch();
	}

	private List<Product> sortProductsByIds(List<Product> fetchedList, List<Long> productIds) {
		Map<Long, Product> productMap = fetchedList.stream()
			.collect(Collectors.toMap(Product::getId, p -> p));

		return productIds.stream()
			.map(productMap::get)
			.filter(Objects::nonNull)
			.toList();
	}

	private BooleanExpression buildBaseCondition() {
		return product.isActive.eq(true)
			.and(product.isHidden.eq(false))
			.and(product.type.eq(BREAD))
			.and(bakery.isActive.eq(true));
	}

	private BooleanExpression buildInterestAreaCondition(Long customerId) {
		if (customerId == null) {
			return TRUE;
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
			return TRUE;
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

	private BooleanExpression recentReservationCondition() {
		return reservationProduct.createdAt.goe(LocalDateTime.now().minusMonths(1));
	}

	private OrderSpecifier<?> buildOrderSpecifier(SortType sort, NumberExpression<Double> distanceExpression) {
		if (sort == LATEST) {
			return product.modifiedAt.desc();
		} else if (sort == POPULAR) {
			return customerProductFavorite.count().desc();
		} else if (sort == DISTANCE) {
			return distanceExpression.asc();
		}
		return product.modifiedAt.desc();
	}
}
