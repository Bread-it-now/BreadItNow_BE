package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.common.infrastructure.jpa.DistanceExpressionProvider;
import com.breaditnow.customer.customer.infrastructure.jpa.QCustomerEntity;
import com.breaditnow.customer.product.application.request.HotProductSearchCriteria;
import com.breaditnow.customer.product.domain.vo.HotSortType;
import com.breaditnow.customer.product.presentation.response.HotProductResponse;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryProductRepository {
    private final JPAQueryFactory queryFactory;
    private static final QProductEntity productEntity = QProductEntity.productEntity;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
    private static final QProductFavoriteEntity productFavoriteEntity = QProductFavoriteEntity.productFavoriteEntity;

    private final DistanceExpressionProvider distanceExpressionProvider;

    public Page<HotProductResponse> fetchHotProducts(Long customerId, HotProductSearchCriteria criteria) {
        DistanceExpressionProvider.Location bakeryLocation = DistanceExpressionProvider.Location.of(bakeryEntity.latitude, bakeryEntity.longitude);
        NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(criteria.location(), bakeryLocation);
        JPAQuery<HotProductResponse> query = queryFactory.select(
                        Projections.fields(
                                HotProductResponse.class,
                                productEntity.id.as("productId"),
                                productEntity.bakeryId.as("bakeryId"),
                                productEntity.name.as("productName"),
                                productEntity.imageUrl.as("productImage"),
                                productEntity.favoriteCount.as("favoriteCount"),
                                // reservationCount 계산 -> startDate와 endDate로 기간 안에 들어있는지 확인
                                productEntity.price.as("price"),
                                productEntity.stock.as("stock"),
                                Expressions.asBoolean(
                                        queryFactory.select(productFavoriteEntity.count())
                                                .from(productFavoriteEntity)
                                                .where(productFavoriteEntity.id.productId.eq(productEntity.id)
                                                        .and(productFavoriteEntity.id.customerId.eq(customerId)))
                                                .gt(0L)
                                ).as("isFavorite"),
                                distanceExpression.as("distance")
                        )
                )
                .from(productEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(productEntity.bakeryId))
                .where(
                        productEntity.isActive.eq(true),
                        productEntity.isHidden.eq(false)
                );

        applySorting(query, criteria.hotSortType());

        Pageable pageable = criteria.pagination().toPageable();
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<HotProductResponse> content = query.fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(productEntity.count())
                .from(productEntity)
                .where(
                        productEntity.isActive.eq(true),
                        productEntity.isHidden.eq(false)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private void applySorting(JPAQuery<?> query, HotSortType sortType) {
        switch (sortType) {
            case FAVORITE -> query.orderBy(Expressions.numberPath(Integer.class, "favoriteCount").desc());
            case RESERVATION -> query.orderBy(Expressions.numberPath(Integer.class, "reservationCount").desc());
        }
    }
}
