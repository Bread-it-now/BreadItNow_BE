//package com.breaditnow.customer.alert.infrastructure.jpa.query;
//
//import com.breaditnow.customer.alert.infrastructure.jpa.entity.QProductAlertEntity;
//import com.breaditnow.customer.alert.presentation.response.ProductAlertResponse;
//import com.breaditnow.customer.alert.presentation.response.TodayProductAlertResponse;
//import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
//import com.breaditnow.customer.common.domain.vo.Pagination;
//import com.breaditnow.customer.product.infrastructure.jpa.entity.QProductEntity;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.support.PageableExecutionUtils;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class QueryProductAlertRepository {
//    private final JPAQueryFactory queryFactory;
//    private static final QProductAlertEntity productAlertEntity = QProductAlertEntity.productAlertEntity;
//    private static final QProductEntity productEntity = QProductEntity.productEntity;
//    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
//
//    public List<TodayProductAlertResponse> getTodayProductAlert(Long customerId) {
//        return queryFactory.select(
//                        Projections.fields(
//                                TodayProductAlertResponse.class,
//                                bakeryEntity.id.as("bakeryId"),
//                                bakeryEntity.name.as("bakeryName"),
//                                productEntity.id.as("productId"),
//                                productEntity.name.as("productName"),
//                                productEntity.releaseTimes.as("releaseTimes")
//                        )
//                )
//                .from(productAlertEntity)
//                .innerJoin(productEntity).on(productEntity.id.eq(productAlertEntity.id.productId))
//                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(productEntity.bakeryId))
//                .where(
//                        productAlertEntity.id.customerId.eq(customerId),
//                        productAlertEntity.isActive.eq(true),
//                        productEntity.isActive.eq(true),
//                        productEntity.isHidden.eq(false)
//                )
//                .fetch();
//    }
//
//    public Page<ProductAlertResponse> getProductAlerts(Long customerId, Pagination pagination) {
//        JPAQuery<ProductAlertResponse> query = createQuery(customerId);
//        Pageable pageable = pagination.toPageable();
//        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
//        JPAQuery<Long> countQuery = createCountQuery(customerId);
//        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchOne);
//    }
//
//    private JPAQuery<ProductAlertResponse> createQuery(Long customerId) {
//        return queryFactory
//                .select(
//                        Projections.fields(
//                                ProductAlertResponse.class,
//                                productEntity.id.as("productId"),
//                                productEntity.name.as("productName"),
//                                productEntity.imageUrl.as("image"),
//                                productEntity.price.as("price"),
//                                productEntity.releaseTimes.as("releaseTimes"),
//                                bakeryEntity.id.as("bakeryId"),
//                                bakeryEntity.name.as("bakeryName"),
//                                productAlertEntity.isActive.as("alertActive")
//                        )
//                )
//                .from(productAlertEntity)
//                .innerJoin(productEntity).on(productEntity.id.eq(productAlertEntity.id.productId))
//                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(productEntity.bakeryId))
//                .where(
//                        productAlertEntity.id.customerId.eq(customerId)
//                );
//    }
//
//    private JPAQuery<Long> createCountQuery(Long customerId) {
//        return queryFactory
//                .select(productAlertEntity.count())
//                .from(productAlertEntity)
//                .where(
//                        productAlertEntity.id.customerId.eq(customerId)
//                );
//    }
//}
