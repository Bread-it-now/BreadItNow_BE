package com.breaditnow.product.domain.port.out;

import com.breaditnow.product.application.port.event.ProductCreatedEvent;
import com.breaditnow.product.application.port.event.ProductDeletedEvent;
import com.breaditnow.product.application.port.event.ProductUpdatedEvent;

public interface PublishProductEventPort {
    void publishProductCreated(ProductCreatedEvent event);
    void publishProductUpdated(ProductUpdatedEvent event);
    void publishProductDeleted(ProductDeletedEvent event);
}
