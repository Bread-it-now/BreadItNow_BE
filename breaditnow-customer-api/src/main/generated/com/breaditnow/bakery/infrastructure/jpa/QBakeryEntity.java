package com.breaditnow.bakery.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBakeryEntity is a Querydsl query type for BakeryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBakeryEntity extends EntityPathBase<BakeryEntity> {

    private static final long serialVersionUID = 695517537L;

    public static final QBakeryEntity bakeryEntity = new QBakeryEntity("bakeryEntity");

    public final com.breaditnow.common.domain.QBaseEntity _super = new com.breaditnow.common.domain.QBaseEntity(this);

    public final StringPath addressDescription = createString("addressDescription");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> favoriteCount = createNumber("favoriteCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final BooleanPath isActive = createBoolean("isActive");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final EnumPath<com.breaditnow.bakery.domain.OperatingStatus> operatingStatus = createEnum("operatingStatus", com.breaditnow.bakery.domain.OperatingStatus.class);

    public final NumberPath<Long> ownerId = createNumber("ownerId", Long.class);

    public final StringPath phone = createString("phone");

    public final StringPath profileImage = createString("profileImage");

    public QBakeryEntity(String variable) {
        super(BakeryEntity.class, forVariable(variable));
    }

    public QBakeryEntity(Path<? extends BakeryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBakeryEntity(PathMetadata metadata) {
        super(BakeryEntity.class, metadata);
    }

}

