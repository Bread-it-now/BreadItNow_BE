package com.breaditnow.domain.domain.product.enumerate;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.domain.global.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductType {
	BREAD, OTHER;

	@JsonCreator
	public static ProductType from(String type) {
		try {
			return ProductType.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new DomainException(INVALID_PRODUCT_TYPE);
		}
	}
}
