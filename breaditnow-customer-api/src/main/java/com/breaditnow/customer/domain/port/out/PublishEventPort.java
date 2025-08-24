package com.breaditnow.customer.domain.port.out;

public interface PublishEventPort {
    void publish(Object event, String routingKey);
}
