package com.breaditnow.owner.bakery.infrastructure.persistence.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBakeryEntity is a Querydsl query type for BakeryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBakeryEntity extends EntityPathBase<BakeryEntity> {

    private static final long serialVersionUID = -297807637L;

    public static final QBakeryEntity bakeryEntity = new QBakeryEntity("bakeryEntity");

    public final com.breaditnow.domain.global.entity.QBaseEntity _super = new com.breaditnow.domain.global.entity.QBaseEntity(this);

    public final ListPath<String, StringPath> additionalImages = this.<String, StringPath>createList("additionalImages", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Integer> favoriteCount = createNumber("favoriteCount", Integer.class);

    public final StringPath fullAddress = createString("fullAddress");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath openTime = createString("openTime");

    public final EnumPath<com.breaditnow.owner.bakery.domain.OperatingStatus> operatingStatus = createEnum("operatingStatus", com.breaditnow.owner.bakery.domain.OperatingStatus.class);

    public final NumberPath<Long> ownerId = createNumber("ownerId", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath regionCode = createString("regionCode");

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

