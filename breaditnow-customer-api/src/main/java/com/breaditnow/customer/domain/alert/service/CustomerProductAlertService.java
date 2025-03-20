package com.breaditnow.customer.domain.alert.service;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.global.exception.CustomerException;
import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.breaditnow.customer.global.exception.CustomerErrorCode.ALERT_NOT_FOUND;
import static com.breaditnow.customer.global.exception.CustomerErrorCode.AUTHENTICATION_REQUIRED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerProductAlertService {

    private final CustomerProductAlertRepository alertRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ApiSuccessResponse<Map<String, Long>> registerProductAlert(Long customerId, Long productId) {
        Customer customer = customerRepository.getById(customerId);
        Product product = productRepository.getById(productId);

        if (alertRepository.existsByCustomerIdAndProductId(customerId, productId)) {
            throw new IllegalStateException("Alert already exists for this product.");
        }

        CustomerProductAlert alert = new CustomerProductAlert(customer, product, true);
        alertRepository.save(alert);

        return ApiSuccessResponse.of("alertId", alert.getId());
    }

    @Transactional
    public void deleteProductAlert(Long customerId, Long productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException(AUTHENTICATION_REQUIRED));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        Optional<CustomerProductAlert> optionalAlert = alertRepository.findByCustomerAndProduct(customer, product);

        if (optionalAlert.isEmpty()) {
            throw new CustomerException(ALERT_NOT_FOUND);
        }

        alertRepository.delete(optionalAlert.get());
    }

    @Transactional
    public boolean toggleProductAlert(Long customerId, Long productId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException(AUTHENTICATION_REQUIRED));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        CustomerProductAlert alert = alertRepository.findByCustomerAndProduct(customer, product)
                .orElseThrow(() -> new NoSuchElementException("Alert not found"));

        boolean newStatus = !alert.isActive();
        alert.setActive(newStatus);

        return newStatus;
    }
}
