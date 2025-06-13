package com.breaditnow.customer.product.infrastructure.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductFavoriteEntityId is a Querydsl query type for ProductFavoriteEntityId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProductFavoriteEntityId extends BeanPath<ProductFavoriteEntityId> {

    private static final long serialVersionUID = 419320219L;

    public static final QProductFavoriteEntityId productFavoriteEntityId = new QProductFavoriteEntityId("productFavoriteEntityId");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QProductFavoriteEntityId(String variable) {
        super(ProductFavoriteEntityId.class, forVariable(variable));
    }

    public QProductFavoriteEntityId(Path<? extends ProductFavoriteEntityId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductFavoriteEntityId(PathMetadata metadata) {
        super(ProductFavoriteEntityId.class, metadata);
    }

}

