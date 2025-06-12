package com.breaditnow.customer.reservation.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrdererEmbeddable is a Querydsl query type for OrdererEmbeddable
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrdererEmbeddable extends BeanPath<OrdererEmbeddable> {

    private static final long serialVersionUID = 800596166L;

    public static final QOrdererEmbeddable ordererEmbeddable = new QOrdererEmbeddable("ordererEmbeddable");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> ordererId = createNumber("ordererId", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public QOrdererEmbeddable(String variable) {
        super(OrdererEmbeddable.class, forVariable(variable));
    }

    public QOrdererEmbeddable(Path<? extends OrdererEmbeddable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrdererEmbeddable(PathMetadata metadata) {
        super(OrdererEmbeddable.class, metadata);
    }

}

