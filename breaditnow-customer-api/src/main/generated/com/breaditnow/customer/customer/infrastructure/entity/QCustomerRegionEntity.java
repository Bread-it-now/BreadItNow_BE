package com.breaditnow.customer.customer.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerRegionEntity is a Querydsl query type for CustomerRegionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerRegionEntity extends EntityPathBase<CustomerRegionEntity> {

    private static final long serialVersionUID = -899502781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerRegionEntity customerRegionEntity = new QCustomerRegionEntity("customerRegionEntity");

    public final com.breaditnow.domain.global.entity.QBaseEntity _super = new com.breaditnow.domain.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QCustomerRegionIdEntity id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QCustomerRegionEntity(String variable) {
        this(CustomerRegionEntity.class, forVariable(variable), INITS);
    }

    public QCustomerRegionEntity(Path<? extends CustomerRegionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerRegionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerRegionEntity(PathMetadata metadata, PathInits inits) {
        this(CustomerRegionEntity.class, metadata, inits);
    }

    public QCustomerRegionEntity(Class<? extends CustomerRegionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCustomerRegionIdEntity(forProperty("id"), inits.get("id")) : null;
    }

}

