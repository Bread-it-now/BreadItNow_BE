package com.breaditnow.customer.alert.domain.port;

import com.breaditnow.customer.alert.domain.ProductAlert;

public interface SaveProductAlertPort {
    void save(ProductAlert productAlert);
    void delete(ProductAlert productAlert);
}
