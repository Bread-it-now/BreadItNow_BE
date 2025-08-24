//package com.breaditnow.customer.bakery.infrastructure.jpa;
//
//import com.breaditnow.customer.bakery.application.request.HotBakerySearchCriteria;
//import com.breaditnow.customer.bakery.presentation.response.BakeryResponse;
//import com.breaditnow.customer.bakery.presentation.response.HotBakeryResponse;
//import com.breaditnow.customer.common.domain.vo.PeriodRange;
//import com.breaditnow.customer.common.infrastructure.jpa.DistanceExpressionProvider;
//import com.breaditnow.customer.product.domain.vo.HotSortType;
//import com.breaditnow.customer.reservation.infrastructure.jpa.entity.QReservationEntity;
//import com.breaditnow.domain.domain.reservation.enumerate.ReservationStatus;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.core.types.dsl.NumberExpression;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.support.PageableExecutionUtils;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class QueryBakeryRepository {
//    private final JPAQueryFactory queryFactory;
//    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;
//    private static final QBakeryFavoriteEntity bakeryFavoriteEntity = QBakeryFavoriteEntity.bakeryFavoriteEntity;
//    private static final QReservationEntity reservationEntity = QReservationEntity.reservationEntity;
//
//    private final DistanceExpressionProvider distanceExpressionProvider;
//
//    public BakeryResponse getBakery(Long customerId, Long bakeryId) {
//        return queryFactory.select(
//                        Projections.fields(
//                                BakeryResponse.class,
//                                bakeryEntity.id.as("bakeryId"),
//                                bakeryEntity.name.as("bakeryName"),
//                                bakeryEntity.addressDescription.as("bakeryAddress"),
//                                bakeryEntity.phone.as("bakeryPhone"),
//                                bakeryEntity.introduction.as("introduction"),
//                                bakeryEntity.profileImage.as("bakeryProfileImage"),
//                                bakeryEntity.operatingStatus.as("operatingStatus"),
//                                Expressions.asBoolean(
//                                        queryFactory.select(bakeryFavoriteEntity.count())
//                                                .from(bakeryFavoriteEntity)
//                                                .where(bakeryFavoriteEntity.id.bakeryId.eq(bakeryId)
//                                                        .and(bakeryFavoriteEntity.id.customerId.eq(customerId))
//                                                        .and(bakeryFavoriteEntity.isActive.eq(true))
//                                                )
//                                                .gt(0L)
//                                ).as("isFavorite")
//                        )
//                )
//                .from(bakeryEntity)
//                .where(bakeryEntity.id.eq(bakeryId)
//                        .and(bakeryEntity.isActive.eq(true))
//                )
//                .fetchOne();
//    }
//
//    public Page<HotBakeryResponse> fetchHotBakeries(Long customerId, HotBakerySearchCriteria criteria) {
//        NumberExpression<Double> distanceExpression = buildDistanceExpression(criteria);
//        JPAQuery<HotBakeryResponse> query = createQuery(customerId, criteria, distanceExpression);
//
//        Pageable pageable = criteria.pagination().toPageable();
//        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
//
//        JPAQuery<Long> countQuery = createCountQuery();
//
//        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchOne);
//    }
//
//    private JPAQuery<HotBakeryResponse> createQuery(Long customerId, HotBakerySearchCriteria criteria, NumberExpression<Double> distanceExpression) {
//        return queryFactory.select(
//                        Projections.fields(
//                                HotBakeryResponse.class,
//                                bakeryEntity.id.as("bakeryId"),
//                                bakeryEntity.name.as("bakeryName"),
//                                bakeryEntity.profileImage.as("bakeryProfileImage"),
//                                distanceExpression.as("distance"),
//                                bakeryEntity.favoriteCount.as("favoriteCount"),
//                                getReservationCountSubquery(criteria.periodRange()).as("reservationCount"),
//                                bakeryEntity.operatingStatus.as("operatingStatus"),
//                                Expressions.asBoolean(
//                                        queryFactory.select(bakeryFavoriteEntity.count())
//                                                .from(bakeryFavoriteEntity)
//                                                .where(bakeryFavoriteEntity.id.bakeryId.eq(bakeryEntity.id)
//                                                        .and(bakeryFavoriteEntity.id.customerId.eq(customerId))
//                                                        .and(bakeryFavoriteEntity.isActive.eq(true))
//                                                )
//                                                .gt(0L)
//                                ).as("isFavorite")
//                        )
//                )
//                .from(bakeryEntity)
//                .where(bakeryEntity.isActive.eq(true))
//                .orderBy(getSortExpression(criteria.hotSortType()), distanceExpression.asc());
//    }
//
//    private NumberExpression<Long> getReservationCountSubquery(PeriodRange periodRange) {
//        if (periodRange == null) {
//            return Expressions.asNumber(0L);
//        }
//
//        return Expressions.numberTemplate(Long.class, "({0})",
//                JPAExpressions.select(
//                        JPAExpressions.select(reservationEntity.id.count())
//                                .from(reservationEntity)
//                                .where(
//                                        reservationEntity.bakeryId.eq(bakeryEntity.id)
//                                                .and(reservationEntity.reservationTime.between(
//                                                        periodRange.startDate().atStartOfDay(),
//                                                        periodRange.endDate().atTime(23, 59, 59)))
//                                                .and(reservationEntity.reservationStatus.eq(ReservationStatus.APPROVED)
//                                                )
//                                )
//                ));
//    }
//
//    private JPAQuery<Long> createCountQuery() {
//        return queryFactory.select(bakeryEntity.count())
//                .from(bakeryEntity)
//                .where(bakeryEntity.isActive.eq(true));
//    }
//
//    private NumberExpression<Double> buildDistanceExpression(HotBakerySearchCriteria criteria) {
//        var bakeryLocation = DistanceExpressionProvider.Location.of(bakeryEntity.latitude, bakeryEntity.longitude);
//        return distanceExpressionProvider.buildDistanceExpression(criteria.location(), bakeryLocation);
//    }
//
//    private OrderSpecifier<?> getSortExpression(HotSortType sortType) {
//        return switch (sortType) {
//            case FAVORITE -> Expressions.numberPath(Integer.class, "favoriteCount").desc();
//            case RESERVATION -> Expressions.numberPath(Integer.class, "reservationCount").desc();
//        };
//    }
//}
