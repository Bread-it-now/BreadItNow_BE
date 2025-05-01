package com.breaditnow.domain.domain.alert.repository;

import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.global.exception.DomainException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

public interface CustomerProductAlertRepository extends JpaRepository<CustomerProductAlert, Long> {

	boolean existsByCustomerIdAndProductId(Long customerId, Long productId);

	@Query("SELECT DISTINCT c " + "FROM CustomerProductAlert cpa " + "JOIN cpa.customer c "
		+ "WHERE cpa.product.id = :productId " + "  AND cpa.isActive = true " + "  AND c.fcmToken IS NOT NULL "
		+ "  AND c.fcmToken <> ''")
	List<Customer> findByProductIdAndIsActiveTrueAndFcmTokenExists(@Param("productId") Long productId);

	Optional<CustomerProductAlert> findByCustomerAndProduct(Customer customer, Product product);

	Page<CustomerProductAlert> findByCustomerId(Long customerId, Pageable pageable);

	List<CustomerProductAlert> findByCustomerId(Long customerId);

	default CustomerProductAlert getByCustomerAndProduct(Customer customer, Product product) {
		return findByCustomerAndProduct(customer, product)
			.orElseThrow(() -> new DomainException(ALERT_NOT_FOUND));
	}

	default CustomerProductAlert getActiveByCustomerAndProduct(Customer customer, Product product) {
		CustomerProductAlert alert = getByCustomerAndProduct(customer, product);
		if (!alert.isActive()) {
			throw new DomainException(ALERT_ALREADY_INACTIVE);
		}
		return alert;
	}

	default void validateAlertNotExists(Long customerId, Long productId) {
		if (existsByCustomerIdAndProductId(customerId, productId)) {
			throw new DomainException(ALERT_ALREADY_EXISTS);
		}
	}
}
