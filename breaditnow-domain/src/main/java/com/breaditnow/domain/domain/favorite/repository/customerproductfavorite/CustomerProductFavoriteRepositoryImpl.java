package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.favorite.repository.GeoDistanceExpressionProvider;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy.ProductFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy.ProductFavoriteSortStrategyFactory;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.vo.GeoPoint;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerProductFavoriteRepositoryImpl implements CustomerProductFavoriteRepositoryCustom {
	private final ProductFavoriteSortStrategyFactory sortFactory;
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<Product> findProductFavorites(Long customerId, Pageable pageable, GeoPoint geoPoint) {
		BooleanExpression baseCondition = customerProductFavorite.customer.id.eq(customerId)
			.and(customerProductFavorite.product.id.eq(product.id))
			.and(customerProductFavorite.isActive.eq(true));

		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);
		ProductFavoriteSortStrategy strategy = sortFactory.getStrategy(pageable.getSort(), distanceExpression);

		List<Product> content = queryFactory
			.select(product)
			.from(customerProductFavorite, product)
			.where(baseCondition)
			.orderBy(strategy.getOrderSpecifier())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long totalCount = queryFactory
			.select(customerProductFavorite.count())
			.from(customerProductFavorite, product)
			.where(customerProductFavorite.customer.id.eq(customerId)
				.and(customerProductFavorite.product.id.eq(product.id))
				.and(customerProductFavorite.isActive.eq(true)))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount == null ? 0 : totalCount);
	}
}
