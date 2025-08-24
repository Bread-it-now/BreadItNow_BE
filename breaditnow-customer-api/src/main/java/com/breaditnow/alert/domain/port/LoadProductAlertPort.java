package com.breaditnow.alert.domain.port;

import com.breaditnow.alert.domain.ProductAlert;

import java.util.Optional;

public interface LoadProductAlertPort {
    Optional<ProductAlert> loadProductAlert(Long customerId, Long productId);
}
