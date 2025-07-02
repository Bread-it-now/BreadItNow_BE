package com.breaditnow.customer.alert.application;

import com.breaditnow.customer.alert.domain.ProductAlert;
import com.breaditnow.customer.alert.domain.port.LoadProductAlertPort;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.ALERT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class ProductAlertValidator {
    private final LoadProductAlertPort loadProductAlertPort;

    public ProductAlert validateProductAlertAndGet(Long customerId, Long productId) {
        return loadProductAlertPort.loadProductAlert(customerId, productId)
                .orElseThrow(() -> new CustomerException(ALERT_NOT_FOUND));
    }
}
