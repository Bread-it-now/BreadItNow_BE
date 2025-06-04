package com.breaditnow.customer.product.application;

import com.breaditnow.customer.customer.application.CustomerService;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.product.application.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductFavoriteService {
    private final SaveProductFavoritePort saveProductFavoritePort;
    private final ProductService productService;
    private final CustomerService customerService;

    @Transactional
    public void addFavoriteProduct(Long customerId, Long productId) {
        Product product = productService.loadProduct(productId);
        Customer customer = customerService.loadCustomer(customerId);
        saveProductFavoritePort.save(customer, product);
    }

    public void removeFavoriteProduct(Long customerId, Long productId) {
    }
}
