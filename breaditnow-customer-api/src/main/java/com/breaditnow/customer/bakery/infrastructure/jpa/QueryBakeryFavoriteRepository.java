package com.breaditnow.customer.bakery.infrastructure.jpa;

import com.breaditnow.customer.bakery.application.request.BakeryFavoriteSearchCriteria;
import com.breaditnow.customer.bakery.presentation.response.BakeryFavoriteResponse;
import com.breaditnow.customer.common.infrastructure.jpa.DistanceExpressionProvider;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryBakeryFavoriteRepository {
    private final JPAQueryFactory queryFactory;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
    private static final QBakeryFavoriteEntity bakeryFavoriteEntity = QBakeryFavoriteEntity.bakeryFavoriteEntity;

    private final DistanceExpressionProvider distanceExpressionProvider;

    public Page<BakeryFavoriteResponse> fetchBakeryFavorites(Long customerId, BakeryFavoriteSearchCriteria criteria) {
        NumberExpression<Double> distanceExpression = buildDistanceExpression(criteria);
        JPAQuery<BakeryFavoriteResponse> query = createQuery(customerId, criteria, distanceExpression);

        Pageable pageable = criteria.pagination().toPageable();
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = createCountQuery(customerId);

        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchOne);
    }

    private JPAQuery<BakeryFavoriteResponse> createQuery(Long customerId, BakeryFavoriteSearchCriteria criteria, NumberExpression<Double> distanceExpression) {
        return queryFactory.select(
                        Projections.fields(
                                BakeryFavoriteResponse.class,
                                bakeryEntity.id.as("bakeryId"),
                                bakeryEntity.name.as("bakeryName"),
                                bakeryEntity.profileImage.as("profileImage"),
                                bakeryEntity.favoriteCount.as("favoriteCount"),
                                bakeryEntity.isActive.as("isBakeryActive"),
                                bakeryEntity.operatingStatus.as("operatingStatus"),
                                distanceExpression.as("distance")
                        )
                )
                .from(bakeryFavoriteEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(bakeryFavoriteEntity.id.bakeryId))
                .where(
                        bakeryFavoriteEntity.id.customerId.eq(customerId),
                        bakeryFavoriteEntity.isActive.eq(true)
                )
                .orderBy(getOrderSpecifiers(criteria.sortType(), distanceExpression));
    }

    private JPAQuery<Long> createCountQuery(Long customerId) {
        return queryFactory.select(bakeryFavoriteEntity.count())
                .from(bakeryFavoriteEntity)
                .where(
                        bakeryFavoriteEntity.id.customerId.eq(customerId),
                        bakeryFavoriteEntity.isActive.eq(true)
                )
                .on(QBakeryFavoriteEntity.bakeryFavoriteEntity.id.customerId.eq(customerId))
                .where(QBakeryFavoriteEntity.bakeryFavoriteEntity.isActive.eq(true));
    }

    private NumberExpression<Double> buildDistanceExpression(BakeryFavoriteSearchCriteria criteria) {
        var bakeryLocation = DistanceExpressionProvider.Location.of(bakeryEntity.latitude, bakeryEntity.longitude);
        return distanceExpressionProvider.buildDistanceExpression(criteria.location(), bakeryLocation);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(SortType sortType, NumberExpression<Double> distanceExpression) {
        return switch (sortType) {
            case DISTANCE -> new OrderSpecifier<?>[]{distanceExpression.asc(), bakeryEntity.id.asc()};
            case POPULAR -> new OrderSpecifier<?>[]{bakeryEntity.favoriteCount.desc(), bakeryEntity.id.asc()};
            default -> new OrderSpecifier<?>[]{bakeryEntity.createdAt.desc(), bakeryEntity.id.asc()}; // 최신순
        };
    }
}
