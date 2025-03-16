package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;
import static com.querydsl.core.types.Projections.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.common.util.GeoPoint;
import com.breaditnow.domain.domain.favorite.dto.BakeryFavoriteDto;
import com.breaditnow.domain.domain.favorite.repository.GeoDistanceExpressionProvider;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy.BakeryFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy.BakeryFavoriteSortStrategyFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerBakeryFavoriteRepositoryImpl implements CustomerBakeryFavoriteRepositoryCustom {
	private final BakeryFavoriteSortStrategyFactory sortFactory;
	private final JPAQueryFactory queryFactory;
	private final GeoDistanceExpressionProvider distanceExpressionProvider;

	@Override
	public Page<BakeryFavoriteDto> findBakeryFavorites(Long customerId, Pageable pageable, GeoPoint geoPoint) {
		BooleanExpression baseCondition = customerBakeryFavorite.customer.id.eq(customerId)
			.and(customerBakeryFavorite.bakery.id.eq(bakery.id))
			.and(customerBakeryFavorite.isActive.eq(true));

		NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(geoPoint,
			bakery);
		BakeryFavoriteSortStrategy strategy = sortFactory.getStrategy(pageable.getSort(), distanceExpression);

		List<BakeryFavoriteDto> content = queryFactory
			.select(
				constructor(BakeryFavoriteDto.class, bakery, distanceExpression)
			)
			.from(customerBakeryFavorite, bakery)
			.where(baseCondition)
			.orderBy(strategy.getOrderSpecifier())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long totalCount = queryFactory
			.select(customerBakeryFavorite.count())
			.from(customerBakeryFavorite, bakery)
			.where(customerBakeryFavorite.customer.id.eq(customerId)
				.and(customerBakeryFavorite.bakery.id.eq(bakery.id))
				.and(customerBakeryFavorite.isActive.eq(true)))
			.fetchOne();

		return new PageImpl<>(content, pageable, totalCount == null ? 0 : totalCount);
	}
}
