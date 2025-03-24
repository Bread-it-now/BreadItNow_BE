package com.breaditnow.domain.domain.alert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import com.breaditnow.domain.domain.customer.entity.Customer;

public interface CustomerProductAlertRepository extends JpaRepository<CustomerProductAlert, Long> {

	boolean existsByCustomerIdAndProductId(Long customerId, Long productId);

	@Query("SELECT DISTINCT c " +
		"FROM CustomerProductAlert cpa " +
		"JOIN cpa.customer c " +
		"WHERE cpa.product.id = :productId " +
		"  AND cpa.isActive = true " +
		"  AND c.fcmToken IS NOT NULL " +
		"  AND c.fcmToken <> ''")
	List<Customer> findByProductIdAndIsActiveTrueAndFcmTokenExists(@Param("productId") Long productId);

}
