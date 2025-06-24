package com.breaditnow.reservation.application.port.in;

import com.breaditnow.reservation.application.dto.event.ProductCreatedEvent;
import com.breaditnow.reservation.application.dto.event.ProductDeletedEvent;
import com.breaditnow.reservation.application.dto.event.ProductUpdatedEvent;

public interface ProductInfoSynchronizationUseCase {
    void createProductInfo(ProductCreatedEvent event);
    void updateProductInfo(ProductUpdatedEvent event);
    void deleteProductInfo(ProductDeletedEvent event);
}
