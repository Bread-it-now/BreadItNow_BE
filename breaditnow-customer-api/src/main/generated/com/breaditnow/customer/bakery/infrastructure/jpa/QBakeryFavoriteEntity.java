package com.breaditnow.customer.bakery.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBakeryFavoriteEntity is a Querydsl query type for BakeryFavoriteEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBakeryFavoriteEntity extends EntityPathBase<BakeryFavoriteEntity> {

    private static final long serialVersionUID = -2008776367L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBakeryFavoriteEntity bakeryFavoriteEntity = new QBakeryFavoriteEntity("bakeryFavoriteEntity");

    public final com.breaditnow.domain.global.entity.QBaseEntity _super = new com.breaditnow.domain.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QBakeryFavoriteEntityId id;

    public final BooleanPath isActive = createBoolean("isActive");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QBakeryFavoriteEntity(String variable) {
        this(BakeryFavoriteEntity.class, forVariable(variable), INITS);
    }

    public QBakeryFavoriteEntity(Path<? extends BakeryFavoriteEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBakeryFavoriteEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBakeryFavoriteEntity(PathMetadata metadata, PathInits inits) {
        this(BakeryFavoriteEntity.class, metadata, inits);
    }

    public QBakeryFavoriteEntity(Class<? extends BakeryFavoriteEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QBakeryFavoriteEntityId(forProperty("id")) : null;
    }

}

