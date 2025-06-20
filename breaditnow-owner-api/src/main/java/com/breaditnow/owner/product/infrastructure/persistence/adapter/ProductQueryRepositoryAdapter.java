package com.breaditnow.owner.product.infrastructure.persistence.adapter;

import com.breaditnow.owner.product.application.port.out.ProductQueryRepository;
import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.domain.ProductStatus;
import com.breaditnow.owner.product.domain.ProductType;
import com.breaditnow.owner.product.infrastructure.persistence.jpa.ProductEntity;
import com.breaditnow.owner.product.infrastructure.presentation.request.ProductSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.breaditnow.owner.product.infrastructure.persistence.jpa.QProductEntity.productEntity;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryAdapter implements ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> search(Long bakeryId, ProductSearchCondition condition) {
        return queryFactory
                .selectFrom(productEntity)
                .where(
                        productEntity.bakeryId.eq(bakeryId),
                        productEntity.deleted.isFalse(),
                        statusEq(condition.status()),
                        typeEq(condition.type())
                )
                .orderBy(productEntity.displayOrder.asc())
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    private BooleanExpression statusEq(ProductStatus status) {
        return status != null ? productEntity.status.eq(status) : null;
    }

    private BooleanExpression typeEq(ProductType type) {
        return type != null ? productEntity.productType.eq(type) : null;
    }
}
