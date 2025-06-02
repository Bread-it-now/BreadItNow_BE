package com.breaditnow.customer.product.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductCategoryEntity is a Querydsl query type for ProductCategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCategoryEntity extends EntityPathBase<ProductCategoryEntity> {

    private static final long serialVersionUID = 2137210611L;

    public static final QProductCategoryEntity productCategoryEntity = new QProductCategoryEntity("productCategoryEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QProductCategoryEntity(String variable) {
        super(ProductCategoryEntity.class, forVariable(variable));
    }

    public QProductCategoryEntity(Path<? extends ProductCategoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCategoryEntity(PathMetadata metadata) {
        super(ProductCategoryEntity.class, metadata);
    }

}

