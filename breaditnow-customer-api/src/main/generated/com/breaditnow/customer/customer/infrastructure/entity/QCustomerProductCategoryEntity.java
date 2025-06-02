package com.breaditnow.customer.customer.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerProductCategoryEntity is a Querydsl query type for CustomerProductCategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerProductCategoryEntity extends EntityPathBase<CustomerProductCategoryEntity> {

    private static final long serialVersionUID = 434477444L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerProductCategoryEntity customerProductCategoryEntity = new QCustomerProductCategoryEntity("customerProductCategoryEntity");

    public final com.breaditnow.domain.global.entity.QBaseEntity _super = new com.breaditnow.domain.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QCustomerProductCategoryIdEntity id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QCustomerProductCategoryEntity(String variable) {
        this(CustomerProductCategoryEntity.class, forVariable(variable), INITS);
    }

    public QCustomerProductCategoryEntity(Path<? extends CustomerProductCategoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerProductCategoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerProductCategoryEntity(PathMetadata metadata, PathInits inits) {
        this(CustomerProductCategoryEntity.class, metadata, inits);
    }

    public QCustomerProductCategoryEntity(Class<? extends CustomerProductCategoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCustomerProductCategoryIdEntity(forProperty("id")) : null;
    }

}

