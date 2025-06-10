package com.breaditnow.customer.bakery.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBakeryFavoriteEntityId is a Querydsl query type for BakeryFavoriteEntityId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBakeryFavoriteEntityId extends BeanPath<BakeryFavoriteEntityId> {

    private static final long serialVersionUID = -1993770420L;

    public static final QBakeryFavoriteEntityId bakeryFavoriteEntityId = new QBakeryFavoriteEntityId("bakeryFavoriteEntityId");

    public final NumberPath<Long> bakeryId = createNumber("bakeryId", Long.class);

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public QBakeryFavoriteEntityId(String variable) {
        super(BakeryFavoriteEntityId.class, forVariable(variable));
    }

    public QBakeryFavoriteEntityId(Path<? extends BakeryFavoriteEntityId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBakeryFavoriteEntityId(PathMetadata metadata) {
        super(BakeryFavoriteEntityId.class, metadata);
    }

}

