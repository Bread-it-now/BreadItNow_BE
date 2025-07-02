package com.breaditnow.customer.customer.adapter.out.persistence.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record CustomerProductCategoryIdEntity(Long customerId, Long productCategoryId) {
}
