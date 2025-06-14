package com.breaditnow.customer.reservation.infrastructure.jpa.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationItemEmbeddable is a Querydsl query type for ReservationItemEmbeddable
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReservationItemEmbeddable extends BeanPath<ReservationItemEmbeddable> {

    private static final long serialVersionUID = -1309458807L;

    public static final QReservationItemEmbeddable reservationItemEmbeddable = new QReservationItemEmbeddable("reservationItemEmbeddable");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productImageUrl = createString("productImageUrl");

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productPrice = createNumber("productPrice", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QReservationItemEmbeddable(String variable) {
        super(ReservationItemEmbeddable.class, forVariable(variable));
    }

    public QReservationItemEmbeddable(Path<? extends ReservationItemEmbeddable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationItemEmbeddable(PathMetadata metadata) {
        super(ReservationItemEmbeddable.class, metadata);
    }

}

