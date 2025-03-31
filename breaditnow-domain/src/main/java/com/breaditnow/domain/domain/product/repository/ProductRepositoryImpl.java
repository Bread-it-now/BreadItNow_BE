package com.breaditnow.domain.domain.product.repository;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.customer.entity.QCustomerRegionPreference.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;
import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;
import static com.breaditnow.domain.domain.reservation.entity.QReservationProduct.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

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

		List<Long> productIds = queryFactory
			.select(product.id)
			.from(product)
			.leftJoin(bakery).on(product.bakery.eq(bakery))
			.leftJoin(reservationProduct).on(reservationProduct.product.eq(product).and(recentReservationCondition()))
			.where(buildBaseCondition().and(buildInterestAreaCondition(customerId)))
			.groupBy(product.id)
			.orderBy(reservationProduct.id.count().desc(), product.id.asc())
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

		List<Product> sortedList = new ArrayList<>();
		for (Long pid : productIds) {
			Product p = productMap.get(pid);
			if (p != null) {
				sortedList.add(p);
			}
		}
		return sortedList;
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

	private BooleanExpression recentReservationCondition() {
		return reservationProduct.createdAt.goe(LocalDateTime.now().minusMonths(1));
	}
}
