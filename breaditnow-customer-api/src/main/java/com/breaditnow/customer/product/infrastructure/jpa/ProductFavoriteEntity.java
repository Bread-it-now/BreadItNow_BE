package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.alert.infrastructure.jpa.ProductAlertEntityId;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.domain.global.entity.BaseEntity;
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
        return new ProductFavorite(id.getCustomerId(), id.getProductId(), isActive);
    }
}
