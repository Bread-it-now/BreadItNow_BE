package com.breaditnow.customer.alert.infrastructure.jpa.entity;

import com.breaditnow.customer.common.domain.BaseEntity;
import com.breaditnow.customer.alert.domain.ProductAlert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "customer_product_alert")
public class ProductAlertEntity extends BaseEntity {
    @EmbeddedId
    private ProductAlertEntityId id;
    private boolean isActive;

    public ProductAlertEntity(ProductAlert productAlert) {
        this.id = new ProductAlertEntityId(productAlert.getCustomerId(), productAlert.getProductId());
        this.isActive = productAlert.isActive();
    }

    public ProductAlert toDomain() {
        return ProductAlert.from(id.getCustomerId(), id.getProductId(), isActive);
    }
}

