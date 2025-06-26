package com.breaditnow.customer.customer.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerRegionEntity is a Querydsl query type for CustomerRegionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerRegionEntity extends EntityPathBase<CustomerRegionEntity> {

    private static final long serialVersionUID = 16383269L;

    public static final QCustomerRegionEntity customerRegionEntity = new QCustomerRegionEntity("customerRegionEntity");

    public final com.breaditnow.customer.common.domain.QBaseEntity _super = new com.breaditnow.customer.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final StringPath gugunCode = createString("gugunCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath sidoCode = createString("sidoCode");

    public QCustomerRegionEntity(String variable) {
        super(CustomerRegionEntity.class, forVariable(variable));
    }

    public QCustomerRegionEntity(Path<? extends CustomerRegionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerRegionEntity(PathMetadata metadata) {
        super(CustomerRegionEntity.class, metadata);
    }

}

