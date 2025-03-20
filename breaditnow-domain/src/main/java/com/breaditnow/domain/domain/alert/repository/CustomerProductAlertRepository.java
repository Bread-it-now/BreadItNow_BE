package com.breaditnow.domain.domain.alert.repository;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;

import java.util.Optional;

public interface CustomerProductAlertRepository extends JpaRepository<CustomerProductAlert, Long> {

	boolean existsByCustomerIdAndProductId(Long customerId, Long productId);

	Optional<CustomerProductAlert> findByCustomerAndProduct(Customer customer, Product product);

	Page<CustomerProductAlert> findByCustomerId(Long customerId, Pageable pageable);
}
