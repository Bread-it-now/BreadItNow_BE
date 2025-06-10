package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.application.response.ProductAlertToggleResponse;
import com.breaditnow.customer.alert.domain.ProductAlert;
import com.breaditnow.customer.alert.domain.port.LoadProductAlertPort;
import com.breaditnow.customer.alert.domain.port.SaveProductAlertPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductAlertService {
    private final LoadProductAlertPort loadProductAlertPort;
    private final SaveProductAlertPort saveProductAlertPort;
    private final ProductAlertValidator productAlertValidator;

    @Transactional
    public void registerProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = loadProductAlertPort.loadProductAlert(customerId, productId)
                .map(alert -> {
                    alert.activate();
                    return alert;
                })
                .orElseGet(() -> ProductAlert.create(customerId, productId));

        saveProductAlertPort.save(productAlert);
    }

    @Transactional
    public void deleteProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = productAlertValidator.validateProductAlertAndGet(customerId, productId);
        productAlert.deactivate();
        saveProductAlertPort.delete(productAlert);
    }

    @Transactional
    public ProductAlertToggleResponse toggleProductAlert(Long customerId, Long productId) {
        ProductAlert productAlert = productAlertValidator.validateProductAlertAndGet(customerId, productId);
        productAlert.toggle();
        saveProductAlertPort.save(productAlert);
        return new ProductAlertToggleResponse(productAlert.isActive());
    }
}
