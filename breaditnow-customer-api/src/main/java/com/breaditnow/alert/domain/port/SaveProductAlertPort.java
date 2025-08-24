package com.breaditnow.alert.domain.port;

import com.breaditnow.alert.domain.ProductAlert;

public interface SaveProductAlertPort {
    void save(ProductAlert productAlert);
    void delete(ProductAlert productAlert);
}
