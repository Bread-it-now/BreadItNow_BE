package com.breaditnow.customer.customer.infrastructure;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.domain.port.LoadCustomerPort;
import com.breaditnow.customer.customer.domain.port.SaveCustomerPort;
import com.breaditnow.customer.customer.infrastructure.jpa.CustomerEntity;
import com.breaditnow.customer.customer.infrastructure.jpa.JpaCustomerRepository;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.CUSTOMER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class CustomerAdapter implements LoadCustomerPort, SaveCustomerPort {
    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = new CustomerEntity(customer);
        entity = jpaCustomerRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(CustomerEntity::toDomain);
    }

    @Override
    public Customer getCustomer(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(CUSTOMER_NOT_FOUND));
    }


    @Override
    public Boolean isExistNickName(String nickname) {
        return jpaCustomerRepository.existsByNickname(nickname);
    }
}
