package com.breaditnow.customer.customer.adapter.out.persistence.entity;

import com.breaditnow.customer.common.domain.BaseEntity;
import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.product.domain.ProductCategory;
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
