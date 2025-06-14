package com.breaditnow.customer.reservation.infrastructure.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationEntityId is a Querydsl query type for ReservationEntityId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReservationEntityId extends BeanPath<ReservationEntityId> {

    private static final long serialVersionUID = -1482522919L;

    public static final QReservationEntityId reservationEntityId = new QReservationEntityId("reservationEntityId");

    public final NumberPath<Long> bakeryId = createNumber("bakeryId", Long.class);

    public final NumberPath<Long> ordererId = createNumber("ordererId", Long.class);

    public QReservationEntityId(String variable) {
        super(ReservationEntityId.class, forVariable(variable));
    }

    public QReservationEntityId(Path<? extends ReservationEntityId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationEntityId(PathMetadata metadata) {
        super(ReservationEntityId.class, metadata);
    }

}

