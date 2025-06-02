package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.response.ProductAlertToggleResponse;
import com.breaditnow.customer.alert.domain.ProductAlert;
import com.breaditnow.customer.alert.domain.port.LoadProductAlertPort;
import com.breaditnow.customer.alert.domain.port.SaveProductAlertPort;
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
    private final LoadProductAlertPort loadProductAlertPort;
    private final SaveProductAlertPort saveProductAlertPort;

    @Transactional
    public void registerProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = ProductAlert.create(customerId, productId);
        if (loadProductAlertPort.isAlerted(productAlert)) {
            throw new CustomerException(ALERT_ALREADY_ACTIVE);
        }
        saveProductAlertPort.save(productAlert);
    }

    @Transactional
    public void deleteProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = ProductAlert.create(customerId, productId);
        if (!loadProductAlertPort.isAlerted(productAlert)) {
            throw new DomainException(ALERT_NOT_FOUND);
        }
        saveProductAlertPort.delete(productAlert);
    }

    @Transactional
    public ProductAlertToggleResponse toggleProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = ProductAlert.create(customerId, productId);
        ProductAlert alert = loadProductAlertPort.findById(productAlert);
        alert.toggle();
        saveProductAlertPort.save(alert);
        return new ProductAlertToggleResponse(alert.isActive());
    }
}
