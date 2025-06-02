package com.breaditnow.customer.customer.infrastructure.jpa;

import jakarta.persistence.Embeddable;

@Embeddable
public record CustomerProductCategoryIdEntity(Long customerId, Long productCategoryId) {
}
