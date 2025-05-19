package com.breaditnow.customer.product.infrastructure.entity;

import com.breaditnow.customer.product.core.ProductCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "product_category")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ProductCategoryEntity {
    @Id
    private Long id;

    private String name;

    public ProductCategory toProductCategory() {
        return new ProductCategory(id, name);
    }
}
