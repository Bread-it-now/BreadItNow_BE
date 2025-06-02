package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.ProductAlert;

public interface ProductAlertPort {
    void save(ProductAlert productAlert);
    void delete(ProductAlert productAlert);
    boolean isAlerted(ProductAlert productAlert);
    ProductAlert findById(ProductAlert productAlert);
}
