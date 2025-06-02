package com.breaditnow.customer.alert.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductAlertEntity is a Querydsl query type for ProductAlertEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductAlertEntity extends EntityPathBase<ProductAlertEntity> {

    private static final long serialVersionUID = -1456252070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductAlertEntity productAlertEntity = new QProductAlertEntity("productAlertEntity");

    public final com.breaditnow.domain.global.entity.QBaseEntity _super = new com.breaditnow.domain.global.entity.QBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QProductAlertEntityId id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QProductAlertEntity(String variable) {
        this(ProductAlertEntity.class, forVariable(variable), INITS);
    }

    public QProductAlertEntity(Path<? extends ProductAlertEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductAlertEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductAlertEntity(PathMetadata metadata, PathInits inits) {
        this(ProductAlertEntity.class, metadata, inits);
    }

    public QProductAlertEntity(Class<? extends ProductAlertEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QProductAlertEntityId(forProperty("id")) : null;
    }

}

