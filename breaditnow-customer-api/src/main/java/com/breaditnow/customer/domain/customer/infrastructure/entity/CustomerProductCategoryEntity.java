package com.breaditnow.customer.domain.customer.infrastructure.entity;

import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.product.core.ProductCategory;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "customer_product_category")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CustomerProductCategoryEntity extends BaseEntity {
    @EmbeddedId
    private CustomerProductCategoryIdEntity id;

    public CustomerProductCategoryEntity(Customer customer, ProductCategory productCategory) {
        this.id = new CustomerProductCategoryIdEntity(customer.getId(), productCategory.getId());
    }
}
