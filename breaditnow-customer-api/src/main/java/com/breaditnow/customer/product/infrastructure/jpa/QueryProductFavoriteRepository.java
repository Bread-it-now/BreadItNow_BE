package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.breaditnow.customer.product.application.request.ProductFavoriteSearchCriteria;
import com.breaditnow.customer.product.presentation.response.ProductFavoriteDetailsResponse;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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

    public Page<ProductFavoriteDetailsResponse> fetchProductFavoriteList(Long customerId, ProductFavoriteSearchCriteria criteria) {
        JPAQuery<ProductFavoriteDetailsResponse> query = queryFactory.select(
                        Projections.fields(
                                ProductFavoriteDetailsResponse.class,
                                productEntity.id.as("productId"),
                                productEntity.bakeryId,
                                productEntity.name,
                                productEntity.imageUrl.as("image"),
                                productEntity.price.as("price"),
                                productEntity.releaseTimes.as("releaseTimes"),
                                ExpressionUtils.as(Expressions.constant(true), "bakeryActive"),
                                productEntity.isActive.as("isProductActive")
                        )
                )
                .from(productFavoriteEntity)
                .innerJoin(productEntity).on(productEntity.id.eq(productFavoriteEntity.id.productId))
                .where(
                        productFavoriteEntity.id.customerId.eq(customerId),
                        productFavoriteEntity.isActive.eq(true)
                );

        applySorting(query, criteria.getSortType(), criteria.getLocation());

        Pageable pageable = criteria.getPagination().toPageable();
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

    private void applySorting(JPAQuery<?> query, SortType sortType, GeoPoint location) {
        if (sortType == null) {
            query.orderBy(productFavoriteEntity.createdAt.desc(), productEntity.id.asc());
            return;
        }

        switch (sortType) {
            case DISTANCE -> {
//                NumberTemplate<Double> distance = Expressions.numberTemplate(Double.class,
//                        "ST_Distance_Sphere(POINT({0}, {1}), POINT(bakery.longitude, bakery.latitude))",
//                        location.longitude(), location.latitude());
//                query.orderBy(distance.asc(), productEntity.id.asc());
                query.orderBy(productFavoriteEntity.createdAt.desc(), productEntity.id.asc());
            }
            case POPULAR -> query.orderBy(productEntity.favoriteCount.desc(), productEntity.id.asc());
            default -> query.orderBy(productFavoriteEntity.createdAt.desc(), productEntity.id.asc());
        }
    }
}
