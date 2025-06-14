package com.breaditnow.customer.region.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegionEntity is a Querydsl query type for RegionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegionEntity extends EntityPathBase<RegionEntity> {

    private static final long serialVersionUID = 485608687L;

    public static final QRegionEntity regionEntity = new QRegionEntity("regionEntity");

    public final StringPath dongCode = createString("dongCode");

    public final StringPath dongName = createString("dongName");

    public final StringPath gugunCode = createString("gugunCode");

    public final StringPath gugunName = createString("gugunName");

    public final StringPath regionCode = createString("regionCode");

    public final StringPath sidoCode = createString("sidoCode");

    public final StringPath sidoName = createString("sidoName");

    public QRegionEntity(String variable) {
        super(RegionEntity.class, forVariable(variable));
    }

    public QRegionEntity(Path<? extends RegionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegionEntity(PathMetadata metadata) {
        super(RegionEntity.class, metadata);
    }

}

