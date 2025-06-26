package com.breaditnow.owner.product.infrastructure.adapter.out.persistence.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = 1981316718L;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final com.breaditnow.owner.common.domain.QBaseEntity _super = new com.breaditnow.owner.common.domain.QBaseEntity(this);

    public final NumberPath<Long> bakeryId = createNumber("bakeryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<com.breaditnow.owner.product.domain.ProductType> productType = createEnum("productType", com.breaditnow.owner.product.domain.ProductType.class);

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final ListPath<String, StringPath> releaseTimes = this.<String, StringPath>createList("releaseTimes", String.class, StringPath.class, PathInits.DIRECT2);

    public final EnumPath<com.breaditnow.owner.product.domain.ProductStatus> status = createEnum("status", com.breaditnow.owner.product.domain.ProductStatus.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProductEntity(String variable) {
        super(ProductEntity.class, forVariable(variable));
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductEntity(PathMetadata metadata) {
        super(ProductEntity.class, metadata);
    }

}

