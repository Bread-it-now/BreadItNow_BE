package com.breaditnow.customer.domain.product.service;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.domain.global.exception.DomainException;

public enum ProductSortStrategy {
	FAVORITE {
		@Override
		public Page<Product> search(Long customerId, Pageable pageable, ProductRepository repository) {
			return repository.searchHotProductsByFavorite(customerId, pageable);
		}
	},
	RESERVATION {
		@Override
		public Page<Product> search(Long customerId, Pageable pageable, ProductRepository repository) {
			return repository.searchHotProductsByReservation(customerId, pageable);
		}
	};

	public abstract Page<Product> search(Long customerId, Pageable pageable, ProductRepository repository);

	public static ProductSortStrategy from(String sort) {
		if ("favorite".equalsIgnoreCase(sort)) {
			return FAVORITE;
		} else if ("reservation".equalsIgnoreCase(sort)) {
			return RESERVATION;
		}
		throw new DomainException(PRODUCT_SORT_CONDITION_NOT_FOUND);
	}
}
