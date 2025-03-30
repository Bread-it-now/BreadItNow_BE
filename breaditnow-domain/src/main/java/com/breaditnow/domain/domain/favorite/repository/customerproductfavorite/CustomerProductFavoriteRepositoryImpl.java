package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.favorite.repository.GeoDistanceExpressionProvider;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy.ProductFavoriteSortStrategyFactory;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.vo.GeoPoint;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerProductFavoriteRepositoryImpl implements CustomerProductFavoriteRepositoryCustom {
	private final ProductFavoriteSortStrategyFactory sortFactory;
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<Product> findProductFavorites(Long customerId, Pageable pageable, String sort, GeoPoint geoPoint) {
		BooleanExpression baseCondition = customerProductFavorite.customer.id.eq(customerId)
			.and(customerProductFavorite.product.id.eq(product.id))
			.and(customerProductFavorite.isActive.eq(true));

		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);
		// ProductFavoriteSortStrategy strategy = sortFactory.getStrategy(pageable.getSort(), distanceExpression);

		JPAQuery<Product> query = queryFactory
			.select(product)
			.from(product)
			.leftJoin(customerProductFavorite).on(customerProductFavorite.product.eq(product))
			.where(baseCondition)
			.groupBy(product.id)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize());

		query.orderBy(buildOrderSpecifier(sort, distanceExpression));

		Long totalCount = queryFactory
			.select(customerProductFavorite.count())
			.from(customerProductFavorite, product)
			.where(baseCondition)
			.fetchOne();

		return new PageImpl<>(query.fetch(), pageable, totalCount == null ? 0 : totalCount);
	}

	private OrderSpecifier<?> buildOrderSpecifier(String sort, NumberExpression<Double> distanceExpression) {
		if ("latest".equalsIgnoreCase(sort)) {
			return customerProductFavorite.modifiedAt.desc();
		} else if ("popular".equalsIgnoreCase(sort)) {
			return customerProductFavorite.count().desc();
		} else if ("distance".equalsIgnoreCase(sort)) {
			return distanceExpression.asc();
		}
		return customerProductFavorite.modifiedAt.desc();
	}
}
