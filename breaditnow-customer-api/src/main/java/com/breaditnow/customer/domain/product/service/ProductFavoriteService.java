package com.breaditnow.customer.domain.product.service;

import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.favorite.entity.CustomerProductFavorite;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFavoriteService {
	private final CustomerProductFavoriteRepository customerProductFavoriteRepository;
	private final ProductRepository productRepository;
	private final CustomerRepository customerRepository;

	@Transactional
	public Long likeProduct(Long customerId, Long productId) {
		productRepository.checkProductIsAlive(productId);
		return customerProductFavoriteRepository.findByCustomerIdAndProductId(customerId, productId)
			.map(favorite -> {
				favorite.changeActive(true);
				return productId;
			})
			.orElseGet(() -> {
				Customer customer = customerRepository.getById(customerId);
				Product product = productRepository.getByTypeAndId(BREAD, productId);

				CustomerProductFavorite newFavorite = CustomerProductFavorite.builder()
					.customer(customer)
					.product(product)
					.build();

				customerProductFavoriteRepository.save(newFavorite);
				return product.getId();
			});
	}

	@Transactional
	public Long deleteProduct(Long customerId, Long productId) {
		CustomerProductFavorite customerProductFavorite = customerProductFavoriteRepository.getByCustomerIdAndProductId(
			customerId, productId);

		customerProductFavorite.changeActive(false);

		return productId;
	}

}
