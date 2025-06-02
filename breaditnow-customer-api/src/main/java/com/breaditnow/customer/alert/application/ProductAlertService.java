package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.response.ProductAlertToggleResponse;
import com.breaditnow.customer.alert.domain.ProductAlert;
import com.breaditnow.customer.alert.domain.port.ProductAlertPort;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.ALERT_ALREADY_ACTIVE;
import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class ProductAlertService {
    private final ProductAlertPort productAlertPort;

    @Transactional
    public void registerProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = ProductAlert.create(customerId, productId);
        if (productAlertPort.isAlerted(productAlert)) {
            throw new CustomerException(ALERT_ALREADY_ACTIVE);
        }
        productAlertPort.save(productAlert);
    }

    @Transactional
    public void deleteProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = ProductAlert.create(customerId, productId);
        if (!productAlertPort.isAlerted(productAlert)) {
            throw new DomainException(ALERT_NOT_FOUND);
        }
        productAlertPort.delete(productAlert);
    }

    @Transactional
    public ProductAlertToggleResponse toggleProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = ProductAlert.create(customerId, productId);
        ProductAlert alert = productAlertPort.findById(productAlert);
        alert.toggle();
        productAlertPort.save(alert);
        return new ProductAlertToggleResponse(alert.isActive());
    }
}
