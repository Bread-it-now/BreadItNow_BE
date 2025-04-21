package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.breaditnow.domain.global.provider.GeoDistanceExpressionProvider;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerProductFavoriteRepositoryImpl implements CustomerProductFavoriteRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<Product> findProductFavorites(Long customerId, Pageable pageable, SortType sort, GeoPoint geoPoint) {
		BooleanExpression baseCondition = buildBaseCondition(customerId);

		JPAQuery<Product> query = queryFactory
			.select(product)
			.from(customerProductFavorite)
			.join(customerProductFavorite.product, product)
			.where(baseCondition)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		applySortCondition(sort, geoPoint, query);

		Long totalCount = queryFactory
			.select(customerProductFavorite.count())
			.from(customerProductFavorite)
			.where(baseCondition)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private BooleanExpression buildBaseCondition(Long customerId) {
		return customerProductFavorite.customer.id.eq(customerId)
			.and(customerProductFavorite.isActive.eq(true));
	}

	private void applySortCondition(SortType sort, GeoPoint geoPoint, JPAQuery<Product> query) {
		switch (sort) {
			case LATEST -> query.orderBy(product.modifiedAt.desc(), product.id.asc());
			case POPULAR -> query
				.leftJoin(customerProductFavorite)
				.on(customerProductFavorite.product.eq(product))
				.groupBy(product.id)
				.orderBy(customerProductFavorite.count().desc(), product.id.asc());
			case DISTANCE -> query
				.orderBy(distanceExpressionProvider.buildDistanceExpression(geoPoint, bakery).asc(), product.id.asc());
			default -> query.orderBy(product.modifiedAt.desc(), product.id.asc());
		}
	}
}
