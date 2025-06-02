package com.breaditnow.customer.product.infrastructure.entity;

import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private boolean isActive;


    public Product toDomain() {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .isActive(isActive)
                .build();
    }
}
