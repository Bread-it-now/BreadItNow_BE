package com.breaditnow.customer.reservation.infrastructure.jpa;

import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.reservation.application.request.ReservationSearchCriteria;
import com.breaditnow.customer.reservation.infrastructure.jpa.dto.ReservationWithBakery;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueryReservationRepository {
    private final JPAQueryFactory queryFactory;
    private static final QReservationEntity reservationEntity = QReservationEntity.reservationEntity;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;

    public Optional<ReservationWithBakery> getReservation(Long customerId, Long reservationId) {
        Tuple tuple = queryFactory
                .select(bakeryEntity, reservationEntity)
                .from(reservationEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(reservationEntity.bakeryId))
                .where(reservationEntity.orderer.ordererId.eq(customerId)
                        .and(reservationEntity.id.eq(reservationId)))
                .fetchOne();

        if (tuple == null) return Optional.empty();
        return Optional.of(new ReservationWithBakery(tuple.get(bakeryEntity), tuple.get(reservationEntity)));
    }

    public Page<ReservationWithBakery> fetchReservations(Long customerId, ReservationSearchCriteria criteria) {
        Pageable pageable = criteria.pagination().toPageable();
        BooleanExpression predicate = buildPredicate(customerId, criteria);

        List<Tuple> tuples = queryFactory
                .select(bakeryEntity, reservationEntity)
                .from(reservationEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(reservationEntity.bakeryId))
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reservationEntity.reservationTime.desc())
                .fetch();

        List<ReservationWithBakery> content = tuples.stream()
                .map(tuple -> new ReservationWithBakery(tuple.get(bakeryEntity), tuple.get(reservationEntity)))
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(reservationEntity.count())
                .from(reservationEntity)
                .where(predicate);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression buildPredicate(Long customerId, ReservationSearchCriteria criteria) {
        BooleanExpression predicate = reservationEntity.orderer.ordererId.eq(customerId);
        if (criteria.status() != null) {
            predicate = predicate.and(reservationEntity.reservationStatus.eq(criteria.status()));
        }
        return predicate;
    }
}
