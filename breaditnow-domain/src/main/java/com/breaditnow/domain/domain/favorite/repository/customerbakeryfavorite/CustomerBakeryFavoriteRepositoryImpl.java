package com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite;

import static com.breaditnow.domain.domain.bakery.entity.QBakery.*;
import static com.breaditnow.domain.domain.favorite.entity.QCustomerBakeryFavorite.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy.BakeryFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.strategy.BakeryFavoriteSortStrategyFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerBakeryFavoriteRepositoryImpl implements CustomerBakeryFavoriteRepositoryCustom {
	private final BakeryFavoriteSortStrategyFactory sortFactory;
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Bakery> findBakeryFavorites(Long customerId, Pageable pageable) {
		BooleanExpression baseCondition = customerBakeryFavorite.customer.id.eq(customerId)
			.and(customerBakeryFavorite.bakery.id.eq(bakery.id))
			.and(customerBakeryFavorite.isActive.eq(true));

		BakeryFavoriteSortStrategy strategy = sortFactory.getStrategy(pageable.getSort());

		List<Bakery> content = queryFactory
			.select(bakery)
			.from(customerBakeryFavorite, bakery)
			.where(baseCondition)
			.orderBy(strategy.getOrderSpecifier(bakery, customerBakeryFavorite))
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
