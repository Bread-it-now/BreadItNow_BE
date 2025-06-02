package com.breaditnow.customer.alert.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductAlertEntityId is a Querydsl query type for ProductAlertEntityId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProductAlertEntityId extends BeanPath<ProductAlertEntityId> {

    private static final long serialVersionUID = -373975425L;

    public static final QProductAlertEntityId productAlertEntityId = new QProductAlertEntityId("productAlertEntityId");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QProductAlertEntityId(String variable) {
        super(ProductAlertEntityId.class, forVariable(variable));
    }

    public QProductAlertEntityId(Path<? extends ProductAlertEntityId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductAlertEntityId(PathMetadata metadata) {
        super(ProductAlertEntityId.class, metadata);
    }

}

