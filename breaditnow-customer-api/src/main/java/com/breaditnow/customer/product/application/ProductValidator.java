package com.breaditnow.customer.product.application;

import com.breaditnow.customer.product.application.port.LoadProductPort;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductValidator {
    private final LoadProductPort loadProductPort;

    public Product validateAndGetProduct(Long productId) {
        return loadProductPort.loadProduct(productId)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }

    public void validateProductExists(Long productId) {
        if (loadProductPort.loadProduct(productId).isEmpty()) {
            throw new DomainException(PRODUCT_NOT_FOUND);
        }
    }
}

