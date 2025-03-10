package com.breaditnow.domain.domain.favorite.repository.customerproductfavorite;

import static com.breaditnow.domain.domain.favorite.entity.QCustomerProductFavorite.*;
import static com.breaditnow.domain.domain.product.entity.QProduct.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy.ProductFavoriteSortStrategy;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.strategy.ProductFavoriteSortStrategyFactory;
import com.breaditnow.domain.domain.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerProductFavoriteRepositoryImpl implements CustomerProductFavoriteRepositoryCustom {
	private final ProductFavoriteSortStrategyFactory sortFactory;
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Product> findProductFavorites(Long customerId, Pageable pageable) {
		BooleanExpression baseCondition = customerProductFavorite.customer.id.eq(customerId)
			.and(customerProductFavorite.product.id.eq(product.id))
			.and(customerProductFavorite.isActive.eq(true));

		ProductFavoriteSortStrategy strategy = sortFactory.getStrategy(pageable.getSort());

		List<Product> content = queryFactory
			.select(product)
			.from(customerProductFavorite, product)
			.where(baseCondition)
			.orderBy(strategy.getOrderSpecifier(product, customerProductFavorite))
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
