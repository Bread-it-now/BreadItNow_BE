package com.breaditnow.customer.product.infrastructure.jpa.entity;

import com.breaditnow.customer.common.domain.BaseEntity;
import com.breaditnow.customer.product.domain.ProductFavorite;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "customer_product_favorite")
public class ProductFavoriteEntity extends BaseEntity {
    @EmbeddedId
    private ProductFavoriteEntityId id;
    private boolean isActive;

    public ProductFavoriteEntity(ProductFavorite productFavorite) {
        this.id = new ProductFavoriteEntityId(productFavorite.getCustomerId(), productFavorite.getProductId());
        this.isActive = productFavorite.isActive();
    }

    public ProductFavorite toDomain() {
        return ProductFavorite.builder()
                .customerId(id.getCustomerId())
                .productId(id.getProductId())
                .isActive(isActive)
                .build();
    }
}
