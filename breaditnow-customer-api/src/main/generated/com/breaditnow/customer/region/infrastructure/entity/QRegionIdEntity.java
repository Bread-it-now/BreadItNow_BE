package com.breaditnow.customer.region.infrastructure.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegionIdEntity is a Querydsl query type for RegionIdEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRegionIdEntity extends BeanPath<RegionIdEntity> {

    private static final long serialVersionUID = -431279382L;

    public static final QRegionIdEntity regionIdEntity = new QRegionIdEntity("regionIdEntity");

    public final StringPath dongCode = createString("dongCode");

    public final StringPath gugunCode = createString("gugunCode");

    public final StringPath sidoCode = createString("sidoCode");

    public QRegionIdEntity(String variable) {
        super(RegionIdEntity.class, forVariable(variable));
    }

    public QRegionIdEntity(Path<? extends RegionIdEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegionIdEntity(PathMetadata metadata) {
        super(RegionIdEntity.class, metadata);
    }

}

