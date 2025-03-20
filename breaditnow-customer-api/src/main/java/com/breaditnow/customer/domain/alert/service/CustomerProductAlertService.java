package com.breaditnow.customer.domain.alert.service;

import com.breaditnow.common.response.ApiSuccessResponse;
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
}
