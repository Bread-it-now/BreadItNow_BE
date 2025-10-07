package com.breaditnow.customer.adapter.in.event;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.event.AccountCreatedEvent;
import com.breaditnow.config.RabbitMQConfig;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private final CustomerRepository customerRepository;

    @RabbitListener(queues = RabbitMQConfig.ACCOUNT_CREATED_QUEUE_NAME)
    public void handleAccountCreated(AccountCreatedEvent event) {
        if (event.getRole() == Role.CUSTOMER) {
            log.info("Creating customer for account: {}", event.getAccountId());
            Customer customer = Customer.builder().id(event.getAccountId()).build();
            customerRepository.save(customer);
        }
    }
}
