package com.breaditnow.customer.domain.alert.service;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.alert.controller.res.CustomerProductAlertPageResponse;
import com.breaditnow.customer.domain.alert.controller.res.CustomerProductAlertResponse;
import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

        alertRepository.validateAlertNotExists(customerId, productId);

        CustomerProductAlert alert = new CustomerProductAlert(customer, product, true);
        alertRepository.save(alert);

        return ApiSuccessResponse.of("alertId", alert.getId());
    }

    @Transactional
    public void deactivateProductAlert(Long customerId, Long productId) {
        Customer customer = customerRepository.getById(customerId);
        Product product = productRepository.getById(productId);
        CustomerProductAlert alert = alertRepository.getActiveByCustomerAndProduct(customer, product);

        alert.setActive(false);
        alertRepository.save(alert);
    }

    @Transactional
    public boolean toggleProductAlert(Long customerId, Long productId) {

        Customer customer = customerRepository.getById(customerId);
        Product product = productRepository.getById(productId);
        CustomerProductAlert alert = alertRepository.getByCustomerAndProduct(customer, product);


        boolean newStatus = !alert.isActive();
        alert.setActive(newStatus);

        return newStatus;
    }

    public CustomerProductAlertPageResponse getProductAlerts(Long customerId, int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerProductAlert> alertsPage = alertRepository.findByCustomerId(customerId, pageable);

        List<CustomerProductAlertResponse> alertResponses = alertsPage.getContent().stream()
                .map(CustomerProductAlertResponse::of)
                .toList();

        return CustomerProductAlertPageResponse.of(alertResponses, alertsPage);
    }
}
