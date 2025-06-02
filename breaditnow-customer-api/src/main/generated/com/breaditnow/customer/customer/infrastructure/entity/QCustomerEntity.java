package com.breaditnow.customer.customer.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerEntity is a Querydsl query type for CustomerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerEntity extends EntityPathBase<CustomerEntity> {

    private static final long serialVersionUID = 3443247L;

    public static final QCustomerEntity customerEntity = new QCustomerEntity("customerEntity");

    public final com.breaditnow.domain.global.entity.QBaseEntity _super = new com.breaditnow.domain.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath initialSetup = createBoolean("initialSetup");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath oauth2Id = createString("oauth2Id");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<com.breaditnow.customer.customer.domain.Provider> provider = createEnum("provider", com.breaditnow.customer.customer.domain.Provider.class);

    public QCustomerEntity(String variable) {
        super(CustomerEntity.class, forVariable(variable));
    }

    public QCustomerEntity(Path<? extends CustomerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerEntity(PathMetadata metadata) {
        super(CustomerEntity.class, metadata);
    }

}

