package com.breaditnow.customer.domain.customer.infrastructure.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record CustomerProductCategoryIdEntity(Long customerId, Long productCategoryId) {
}
