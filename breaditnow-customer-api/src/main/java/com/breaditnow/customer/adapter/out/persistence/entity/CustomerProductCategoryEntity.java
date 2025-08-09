package com.breaditnow.customer.adapter.out.persistence.entity;

import com.breaditnow.common.domain.BaseEntity;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.product.domain.ProductCategory;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "customer_product_category")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Getter
public class CustomerProductCategoryEntity extends BaseEntity {
    @EmbeddedId
    private CustomerProductCategoryIdEntity id;

    public CustomerProductCategoryEntity(Customer customer, ProductCategory productCategory) {
        this.id = new CustomerProductCategoryIdEntity(customer.getId(), productCategory.getId());
    }
}
