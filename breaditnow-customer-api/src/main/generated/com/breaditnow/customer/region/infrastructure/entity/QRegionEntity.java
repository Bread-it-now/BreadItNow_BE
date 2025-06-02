package com.breaditnow.customer.region.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegionEntity is a Querydsl query type for RegionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegionEntity extends EntityPathBase<RegionEntity> {

    private static final long serialVersionUID = 485608687L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegionEntity regionEntity = new QRegionEntity("regionEntity");

    public final StringPath dongName = createString("dongName");

    public final StringPath gugunName = createString("gugunName");

    public final QRegionIdEntity id;

    public final StringPath sidoName = createString("sidoName");

    public QRegionEntity(String variable) {
        this(RegionEntity.class, forVariable(variable), INITS);
    }

    public QRegionEntity(Path<? extends RegionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegionEntity(PathMetadata metadata, PathInits inits) {
        this(RegionEntity.class, metadata, inits);
    }

    public QRegionEntity(Class<? extends RegionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QRegionIdEntity(forProperty("id")) : null;
    }

}

