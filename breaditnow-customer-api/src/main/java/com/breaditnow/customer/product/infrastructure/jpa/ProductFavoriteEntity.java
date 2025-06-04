package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.alert.infrastructure.jpa.ProductAlertEntityId;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.domain.Product;
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
    private ProductAlertEntityId id;
    private boolean isActive;

    public ProductFavoriteEntity(Customer customer, Product product) {
        this.id = new ProductAlertEntityId(customer.getId(), product.getId());
        this.isActive = true;
    }
}
