package com.breaditnow.customer.product.infrastructure.jpa.query;

import com.breaditnow.customer.alert.infrastructure.jpa.entity.QProductAlertEntity;
import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.common.infrastructure.jpa.DistanceExpressionProvider;
import com.breaditnow.customer.product.application.request.HotProductSearchCriteria;
import com.breaditnow.customer.product.domain.ProductType;
import com.breaditnow.customer.product.domain.vo.HotSortType;
import com.breaditnow.customer.product.infrastructure.jpa.entity.QProductEntity;
import com.breaditnow.customer.product.infrastructure.jpa.entity.QProductFavoriteEntity;
import com.breaditnow.customer.product.presentation.response.BreadProductResponse;
import com.breaditnow.customer.product.presentation.response.HotProductResponse;
import com.breaditnow.customer.product.presentation.response.OtherProductResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
public class QueryProductRepository {
    private final JPAQueryFactory queryFactory;
    private static final QProductEntity productEntity = QProductEntity.productEntity;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
    private static final QProductFavoriteEntity productFavoriteEntity = QProductFavoriteEntity.productFavoriteEntity;
    private static final QProductAlertEntity productAlertEntity = QProductAlertEntity.productAlertEntity;
    private final DistanceExpressionProvider distanceExpressionProvider;

    public Page<HotProductResponse> fetchHotProducts(Long customerId, HotProductSearchCriteria criteria) {
        NumberExpression<Double> distanceExpression = buildDistanceExpression(criteria);
        JPAQuery<HotProductResponse> query = createQuery(customerId, criteria, distanceExpression);

        Pageable pageable = criteria.pagination().toPageable();
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = createCountQuery();

        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchOne);
    }

    public List<BreadProductResponse> getBreadProductsByBakeryId(Long bakeryId) {
        return queryFactory.select(
                        Projections.fields(
                                BreadProductResponse.class,
                                productEntity.id.as("productId"),
                                productEntity.name.as("productName"),
                                productEntity.imageUrl.as("productImage"),
                                productEntity.description.as("productDescription"),
                                productEntity.releaseTimes.as("releaseTimes"),
                                Expressions.asBoolean(
                                        queryFactory.select(productFavoriteEntity.count())
                                                .from(productFavoriteEntity)
                                                .where(
                                                        productFavoriteEntity.id.productId.eq(productEntity.id)
                                                                .and(productFavoriteEntity.isActive.eq(true))
                                                )
                                                .gt(0L)
                                ).as("isProductFavorite"),
                                Expressions.asBoolean(
                                        queryFactory.select(productAlertEntity.count())
                                                .from(productAlertEntity)
                                                .where(
                                                        productAlertEntity.id.productId.eq(productEntity.id)
                                                                .and(productAlertEntity.isActive.eq(true))
                                                )
                                                .gt(0L)
                                ).as("isProductAlarm")
                        )
                )
                .from(productEntity)
                .where(
                        productEntity.bakeryId.eq(bakeryId),
                        productEntity.type.eq(ProductType.BREAD),
                        productEntity.isActive.eq(true),
                        productEntity.isHidden.eq(false)
                )
                .orderBy(productEntity.displayOrder.asc())
                .fetch();
    }

    public List<OtherProductResponse> getOtherProductsByBakeryId(Long bakeryId) {
        return queryFactory.select(
                        Projections.fields(
                                OtherProductResponse.class,
                                productEntity.id.as("productId"),
                                productEntity.name.as("productName"),
                                productEntity.imageUrl.as("productImage"),
                                productEntity.description.as("productDescription"),
                                productEntity.price.as("price"),
                                productEntity.stock.as("stock")
                        )
                )
                .from(productEntity)
                .where(
                        productEntity.bakeryId.eq(bakeryId),
                        productEntity.type.eq(ProductType.OTHER),
                        productEntity.isActive.eq(true),
                        productEntity.isHidden.eq(false)
                )
                .fetch();
    }

    private JPAQuery<HotProductResponse> createQuery(Long customerId, HotProductSearchCriteria criteria, NumberExpression<Double> distanceExpression) {
        return queryFactory.select(
                        Projections.fields(
                                HotProductResponse.class,
                                productEntity.id.as("productId"),
                                productEntity.bakeryId.as("bakeryId"),
                                productEntity.name.as("productName"),
                                productEntity.imageUrl.as("productImage"),
                                productEntity.favoriteCount.as("favoriteCount"),
                                productEntity.price.as("price"),
                                productEntity.stock.as("stock"),
                                Expressions.asBoolean(
                                        queryFactory.select(productFavoriteEntity.count())
                                                .from(productFavoriteEntity)
                                                .where(productFavoriteEntity.id.productId.eq(productEntity.id)
                                                        .and(productFavoriteEntity.id.customerId.eq(customerId))
                                                        .and(productFavoriteEntity.isActive.eq(true))
                                                )
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
                )
                .orderBy(getSortExpression(criteria.hotSortType()));
    }

    private JPAQuery<Long> createCountQuery() {
        return queryFactory.select(QProductEntity.productEntity.count())
                .from(QProductEntity.productEntity)
                .where(
                        productEntity.isActive.eq(true),
                        productEntity.isHidden.eq(false)
                );
    }

    private NumberExpression<Double> buildDistanceExpression(HotProductSearchCriteria criteria) {
        var bakeryLocation = DistanceExpressionProvider.Location.of(bakeryEntity.latitude, bakeryEntity.longitude);
        return distanceExpressionProvider.buildDistanceExpression(criteria.location(), bakeryLocation);
    }

    private OrderSpecifier<?> getSortExpression(HotSortType sortType) {
        return switch (sortType) {
            case FAVORITE -> Expressions.numberPath(Integer.class, "favoriteCount").desc();
            case RESERVATION -> Expressions.numberPath(Integer.class, "reservationCount").desc();
        };
    }
}
