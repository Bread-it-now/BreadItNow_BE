package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.ProductAlert;

public interface LoadProductAlertPort {
    boolean isAlerted(ProductAlert productAlert);
    ProductAlert findById(ProductAlert productAlert);
}
