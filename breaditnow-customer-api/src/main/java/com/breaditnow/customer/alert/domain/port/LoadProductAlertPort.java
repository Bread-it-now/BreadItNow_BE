package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.ProductAlert;

import java.util.Optional;

public interface LoadProductAlertPort {
    Optional<ProductAlert> loadProductAlert(Long customerId, Long productId);
}
