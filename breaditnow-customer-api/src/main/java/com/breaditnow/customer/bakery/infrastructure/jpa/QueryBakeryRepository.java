package com.breaditnow.customer.bakery.infrastructure.jpa;

import com.breaditnow.customer.bakery.presentation.response.BakeryResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueryBakeryRepository {
    private final JPAQueryFactory queryFactory;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
    private static final QBakeryFavoriteEntity bakeryFavoriteEntity = QBakeryFavoriteEntity.bakeryFavoriteEntity;

    public BakeryResponse getBakery(Long customerId, Long bakeryId) {
        return queryFactory.select(
                        Projections.fields(
                                BakeryResponse.class,
                                bakeryEntity.id.as("bakeryId"),
                                bakeryEntity.name.as("name"),
                                bakeryEntity.addressDescription.as("address"),
                                bakeryEntity.phone.as("phone"),
                                bakeryEntity.introduction.as("introduction"),
                                bakeryEntity.profileImage.as("profileImage"),
                                bakeryEntity.operatingStatus.as("operatingStatus"),
                                Expressions.asBoolean(
                                        queryFactory.select(bakeryFavoriteEntity.count())
                                                .from(bakeryFavoriteEntity)
                                                .where(bakeryFavoriteEntity.id.bakeryId.eq(bakeryId)
                                                        .and(bakeryFavoriteEntity.id.customerId.eq(customerId))
                                                        .and(bakeryFavoriteEntity.isActive.eq(true))
                                                )
                                                .gt(0L)
                                ).as("isFavorite")
                        )
                )
                .from(bakeryEntity)
                .where(bakeryEntity.id.eq(bakeryId)
                        .and(bakeryEntity.isActive.eq(true))
                )
                .fetchOne();
    }
}
