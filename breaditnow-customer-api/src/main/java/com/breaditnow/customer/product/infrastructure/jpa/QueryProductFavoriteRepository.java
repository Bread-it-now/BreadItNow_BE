package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.common.infrastructure.jpa.DistanceExpressionProvider;
import com.breaditnow.customer.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.customer.product.presentation.response.ProductFavoriteDetailsResponse;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryProductFavoriteRepository {
    private final JPAQueryFactory queryFactory;
    private static final QProductFavoriteEntity productFavoriteEntity = QProductFavoriteEntity.productFavoriteEntity;
    private static final QProductEntity productEntity = QProductEntity.productEntity;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
    private final DistanceExpressionProvider distanceExpressionProvider;

    public Page<ProductFavoriteDetailsResponse> fetchProductFavoriteList(Long customerId, ProductFavoriteSearchCriteria criteria) {
        NumberExpression<Double> distanceExpression = distanceExpressionProvider.buildDistanceExpression(criteria.location(), bakeryEntity.latitude, bakeryEntity.longitude);

        JPAQuery<ProductFavoriteDetailsResponse> query = queryFactory.select(
                        Projections.fields(
                                ProductFavoriteDetailsResponse.class,
                                productEntity.id.as("productId"),
                                productEntity.bakeryId.as("bakeryId"),
                                productEntity.name.as("productName"),
                                productEntity.imageUrl.as("image"),
                                productEntity.price.as("price"),
                                productEntity.releaseTimes.as("releaseTimes"),
                                bakeryEntity.isActive.as("isBakeryActive"),
                                productEntity.isActive.as("isProductActive"),
                                distanceExpression.as("distance")
                        )
                )
                .from(productFavoriteEntity)
                .innerJoin(productEntity).on(productEntity.id.eq(productFavoriteEntity.id.productId))
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(productEntity.bakeryId))
                .where(
                        productFavoriteEntity.id.customerId.eq(customerId),
                        productFavoriteEntity.isActive.eq(true)
                );

        applySorting(query, criteria.sortType(), distanceExpression);

        Pageable pageable = criteria.pagination().toPageable();
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<ProductFavoriteDetailsResponse> content = query.fetch();


        JPAQuery<Long> countQuery = queryFactory
                .select(productFavoriteEntity.count())
                .from(productFavoriteEntity)
                .where(
                        productFavoriteEntity.id.customerId.eq(customerId),
                        productFavoriteEntity.isActive.eq(true)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private void applySorting(JPAQuery<?> query, SortType sortType, NumberExpression<Double> distanceExpression) {
        if (sortType == null) {
            query.orderBy(productFavoriteEntity.createdAt.desc(), productEntity.id.asc());
            return;
        }

        switch (sortType) {
            case DISTANCE -> query.orderBy(distanceExpression.asc(), productEntity.id.asc());
            case POPULAR -> query.orderBy(productEntity.favoriteCount.desc(), productEntity.id.asc());
            default -> query.orderBy(productFavoriteEntity.createdAt.desc(), productEntity.id.asc()); // latest
        }
    }
}
