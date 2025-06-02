package com.breaditnow.customer.customer.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerRegionIdEntity is a Querydsl query type for CustomerRegionIdEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCustomerRegionIdEntity extends BeanPath<CustomerRegionIdEntity> {

    private static final long serialVersionUID = -83538370L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerRegionIdEntity customerRegionIdEntity = new QCustomerRegionIdEntity("customerRegionIdEntity");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final com.breaditnow.customer.region.infrastructure.entity.QRegionIdEntity regionId;

    public QCustomerRegionIdEntity(String variable) {
        this(CustomerRegionIdEntity.class, forVariable(variable), INITS);
    }

    public QCustomerRegionIdEntity(Path<? extends CustomerRegionIdEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerRegionIdEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerRegionIdEntity(PathMetadata metadata, PathInits inits) {
        this(CustomerRegionIdEntity.class, metadata, inits);
    }

    public QCustomerRegionIdEntity(Class<? extends CustomerRegionIdEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.regionId = inits.isInitialized("regionId") ? new com.breaditnow.customer.region.infrastructure.entity.QRegionIdEntity(forProperty("regionId")) : null;
    }

}

