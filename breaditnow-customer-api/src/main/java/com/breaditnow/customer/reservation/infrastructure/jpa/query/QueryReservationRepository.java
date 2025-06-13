package com.breaditnow.customer.reservation.infrastructure.jpa.query;

import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.reservation.application.request.ReservationSearchCriteria;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationDto;
import com.breaditnow.customer.reservation.infrastructure.jpa.entity.QReservationEntity;
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

    public Optional<ReservationDto> getReservation(Long customerId, Long reservationId) {
        Tuple tuple = queryFactory
                .select(bakeryEntity, reservationEntity)
                .from(reservationEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(reservationEntity.bakeryId))
                .where(reservationEntity.ordererId.eq(customerId)
                        .and(reservationEntity.id.eq(reservationId))
                )
                .fetchOne();

        if (tuple == null) return Optional.empty();
        return Optional.of(new ReservationDto(tuple.get(bakeryEntity), tuple.get(reservationEntity)));
    }

    public Page<ReservationDto> fetchReservations(Long customerId, ReservationSearchCriteria criteria) {
        Pageable pageable = criteria.pagination().toPageable();

        List<Tuple> tuples = queryFactory
                .select(bakeryEntity, reservationEntity)
                .from(reservationEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(reservationEntity.bakeryId))
                .where(reservationEntity.ordererId.eq(customerId)
                        .and(reservationStatusExpression(criteria.status()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reservationEntity.reservationTime.desc())
                .fetch();

        List<ReservationDto> content = tuples.stream()
                .map(tuple -> new ReservationDto(tuple.get(bakeryEntity), tuple.get(reservationEntity)))
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(reservationEntity.count())
                .from(reservationEntity)
                .where(reservationEntity.ordererId.eq(customerId)
                        .and(reservationStatusExpression(criteria.status()))
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression reservationStatusExpression(ReservationStatus status) {
        if (status != null) {
            return reservationEntity.reservationStatus.eq(status);
        }

        return null;
    }
}
