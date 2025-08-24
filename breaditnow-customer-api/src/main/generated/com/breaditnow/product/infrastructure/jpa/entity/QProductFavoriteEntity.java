package com.breaditnow.product.infrastructure.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductFavoriteEntity is a Querydsl query type for ProductFavoriteEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductFavoriteEntity extends EntityPathBase<ProductFavoriteEntity> {

    private static final long serialVersionUID = 394351380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductFavoriteEntity productFavoriteEntity = new QProductFavoriteEntity("productFavoriteEntity");

    public final com.breaditnow.common.domain.QBaseEntity _super = new com.breaditnow.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QProductFavoriteEntityId id;

    public final BooleanPath isActive = createBoolean("isActive");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QProductFavoriteEntity(String variable) {
        this(ProductFavoriteEntity.class, forVariable(variable), INITS);
    }

    public QProductFavoriteEntity(Path<? extends ProductFavoriteEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductFavoriteEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductFavoriteEntity(PathMetadata metadata, PathInits inits) {
        this(ProductFavoriteEntity.class, metadata, inits);
    }

    public QProductFavoriteEntity(Class<? extends ProductFavoriteEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QProductFavoriteEntityId(forProperty("id")) : null;
    }

}

