package com.breaditnow.owner.adapter.in.event;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.event.AccountCreatedEvent;
import com.breaditnow.owner.domain.model.Owner;
import com.breaditnow.owner.domain.port.out.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.breaditnow.config.RabbitMQConfig.ACCOUNT_CREATED_QUEUE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventListener {
    private final OwnerRepository ownerRepository;

    @RabbitListener(queues = ACCOUNT_CREATED_QUEUE_NAME)
    public void handleAccountCreated(AccountCreatedEvent event) {
        if (event.getRole() == Role.OWNER) {
            log.info("Creating owner for account: {}", event.getAccountId());
            Owner owner = Owner.builder().id(event.getAccountId()).build();
            ownerRepository.save(owner);
        }
    }
}
