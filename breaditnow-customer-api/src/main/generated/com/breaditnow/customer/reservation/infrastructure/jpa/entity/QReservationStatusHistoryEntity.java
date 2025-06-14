package com.breaditnow.customer.reservation.infrastructure.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationStatusHistoryEntity is a Querydsl query type for ReservationStatusHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationStatusHistoryEntity extends EntityPathBase<ReservationStatusHistoryEntity> {

    private static final long serialVersionUID = 115737962L;

    public static final QReservationStatusHistoryEntity reservationStatusHistoryEntity = new QReservationStatusHistoryEntity("reservationStatusHistoryEntity");

    public final DateTimePath<java.time.LocalDateTime> changeAt = createDateTime("changeAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.breaditnow.customer.reservation.domain.ReservationStatus> newStatus = createEnum("newStatus", com.breaditnow.customer.reservation.domain.ReservationStatus.class);

    public final EnumPath<com.breaditnow.customer.reservation.domain.ReservationStatus> oldStatus = createEnum("oldStatus", com.breaditnow.customer.reservation.domain.ReservationStatus.class);

    public final NumberPath<Long> reservationId = createNumber("reservationId", Long.class);

    public QReservationStatusHistoryEntity(String variable) {
        super(ReservationStatusHistoryEntity.class, forVariable(variable));
    }

    public QReservationStatusHistoryEntity(Path<? extends ReservationStatusHistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationStatusHistoryEntity(PathMetadata metadata) {
        super(ReservationStatusHistoryEntity.class, metadata);
    }

}

