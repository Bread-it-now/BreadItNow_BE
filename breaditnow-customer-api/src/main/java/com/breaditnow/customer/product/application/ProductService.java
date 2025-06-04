package com.breaditnow.customer.product.application;

import com.breaditnow.customer.product.application.port.LoadProductPort;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final LoadProductPort loadProductPort;

    @Transactional(readOnly = true)
    public Product loadProduct(Long productId) {
        return loadProductPort.loadProduct(productId)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }

    @Transactional
    public Product loadProductWithLock(Long productId) {
        return loadProductPort.loadProductWithLock(productId)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_FOUND));
    }
}
