package com.breaditnow.customer.reservation.infrastructure.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationEntity is a Querydsl query type for ReservationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationEntity extends EntityPathBase<ReservationEntity> {

    private static final long serialVersionUID = -1033943778L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationEntity reservationEntity = new QReservationEntity("reservationEntity");

    public final NumberPath<Long> bakeryId = createNumber("bakeryId", Long.class);

    public final StringPath cancellationReason = createString("cancellationReason");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.breaditnow.customer.reservation.infrastructure.jpa.vo.QOrdererEmbeddable orderer;

    public final DateTimePath<java.time.LocalDateTime> pickupDeadLine = createDateTime("pickupDeadLine", java.time.LocalDateTime.class);

    public final ListPath<com.breaditnow.customer.reservation.infrastructure.jpa.vo.ReservationItemEmbeddable, com.breaditnow.customer.reservation.infrastructure.jpa.vo.QReservationItemEmbeddable> reservationItems = this.<com.breaditnow.customer.reservation.infrastructure.jpa.vo.ReservationItemEmbeddable, com.breaditnow.customer.reservation.infrastructure.jpa.vo.QReservationItemEmbeddable>createList("reservationItems", com.breaditnow.customer.reservation.infrastructure.jpa.vo.ReservationItemEmbeddable.class, com.breaditnow.customer.reservation.infrastructure.jpa.vo.QReservationItemEmbeddable.class, PathInits.DIRECT2);

    public final NumberPath<Long> reservationNumber = createNumber("reservationNumber", Long.class);

    public final EnumPath<com.breaditnow.customer.reservation.domain.ReservationStatus> reservationStatus = createEnum("reservationStatus", com.breaditnow.customer.reservation.domain.ReservationStatus.class);

    public final DateTimePath<java.time.LocalDateTime> reservationTime = createDateTime("reservationTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QReservationEntity(String variable) {
        this(ReservationEntity.class, forVariable(variable), INITS);
    }

    public QReservationEntity(Path<? extends ReservationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationEntity(PathMetadata metadata, PathInits inits) {
        this(ReservationEntity.class, metadata, inits);
    }

    public QReservationEntity(Class<? extends ReservationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderer = inits.isInitialized("orderer") ? new com.breaditnow.customer.reservation.infrastructure.jpa.vo.QOrdererEmbeddable(forProperty("orderer")) : null;
    }

}

