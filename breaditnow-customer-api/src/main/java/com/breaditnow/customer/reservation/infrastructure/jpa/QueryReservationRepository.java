package com.breaditnow.customer.reservation.infrastructure.jpa;

import com.breaditnow.customer.bakery.infrastructure.jpa.QBakeryEntity;
import com.breaditnow.customer.reservation.presentation.response.ReservationDetailResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueryReservationRepository {
    private final JPAQueryFactory queryFactory;
    private static final QReservationEntity reservationEntity = QReservationEntity.reservationEntity;
    private static final QBakeryEntity bakeryEntity = QBakeryEntity.bakeryEntity;

    public Optional<ReservationDetailResponse> getReservationDetail(Long customerId, Long reservationId) {
        Tuple tuple = queryFactory
                .select(bakeryEntity, reservationEntity)
                .from(reservationEntity)
                .innerJoin(bakeryEntity).on(bakeryEntity.id.eq(reservationEntity.bakeryId))
                .where(reservationEntity.orderer.ordererId.eq(customerId)
                        .and(reservationEntity.id.eq(reservationId)))
                .fetchOne();

        if (tuple == null) return Optional.empty();
        return Optional.of(ReservationDetailResponse.from(tuple.get(bakeryEntity), tuple.get(reservationEntity)));
    }
}
