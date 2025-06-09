package com.breaditnow.customer.alert.infrastructure.jpa;

import com.breaditnow.customer.alert.presentation.response.TodayProductAlertResponse;
import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.product.infrastructure.jpa.QProductEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryProductAlertRepository {
    private final JPAQueryFactory queryFactory;
    private static final QProductAlertEntity productAlertEntity = QProductAlertEntity.productAlertEntity;
    private static final QProductEntity productEntity = QProductEntity.productEntity;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;

    public List<TodayProductAlertResponse> getTodayProductAlert(Long customerId) {
        return queryFactory.select(
                        Projections.fields(
                                TodayProductAlertResponse.class,
                                bakeryEntity.id.as("bakeryId"),
                                productEntity.id.as("productId"),
                                productEntity.name.as("productName"),
                                productEntity.releaseTimes.as("releaseTimes")
                        )
                )
                .from(productAlertEntity)
                .innerJoin(productEntity).on(productEntity.id.eq(productAlertEntity.id.productId))
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(productEntity.bakeryId))
                .where(
                        productAlertEntity.id.customerId.eq(customerId),
                        productAlertEntity.isActive.eq(true)
                )
                .fetch();
    }
}
