package com.breaditnow.customer.customer.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerProductCategoryIdEntity is a Querydsl query type for CustomerProductCategoryIdEntity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCustomerProductCategoryIdEntity extends BeanPath<CustomerProductCategoryIdEntity> {

    private static final long serialVersionUID = -1486652131L;

    public static final QCustomerProductCategoryIdEntity customerProductCategoryIdEntity = new QCustomerProductCategoryIdEntity("customerProductCategoryIdEntity");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<Long> productCategoryId = createNumber("productCategoryId", Long.class);

    public QCustomerProductCategoryIdEntity(String variable) {
        super(CustomerProductCategoryIdEntity.class, forVariable(variable));
    }

    public QCustomerProductCategoryIdEntity(Path<CustomerProductCategoryIdEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerProductCategoryIdEntity(PathMetadata metadata) {
        super(CustomerProductCategoryIdEntity.class, metadata);
    }

}

