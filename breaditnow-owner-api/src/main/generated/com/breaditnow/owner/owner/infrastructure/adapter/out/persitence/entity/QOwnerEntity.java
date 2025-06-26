package com.breaditnow.owner.owner.infrastructure.adapter.out.persitence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOwnerEntity is a Querydsl query type for OwnerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOwnerEntity extends EntityPathBase<OwnerEntity> {

    private static final long serialVersionUID = -26238137L;

    public static final QOwnerEntity ownerEntity = new QOwnerEntity("ownerEntity");

    public final StringPath email = createString("email");

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public QOwnerEntity(String variable) {
        super(OwnerEntity.class, forVariable(variable));
    }

    public QOwnerEntity(Path<? extends OwnerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOwnerEntity(PathMetadata metadata) {
        super(OwnerEntity.class, metadata);
    }

}

