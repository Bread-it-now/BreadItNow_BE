package com.breaditnow.owner.reservation.infrastructure.adapter.out.persistence.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationViewEntity is a Querydsl query type for ReservationViewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationViewEntity extends EntityPathBase<ReservationViewEntity> {

    private static final long serialVersionUID = 375140563L;

    public static final QReservationViewEntity reservationViewEntity = new QReservationViewEntity("reservationViewEntity");

    public final NumberPath<Long> bakeryId = createNumber("bakeryId", Long.class);

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final StringPath productInfoJson = createString("productInfoJson");

    public final NumberPath<Long> reservationId = createNumber("reservationId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> reservationTime = createDateTime("reservationTime", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QReservationViewEntity(String variable) {
        super(ReservationViewEntity.class, forVariable(variable));
    }

    public QReservationViewEntity(Path<? extends ReservationViewEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationViewEntity(PathMetadata metadata) {
        super(ReservationViewEntity.class, metadata);
    }

}

