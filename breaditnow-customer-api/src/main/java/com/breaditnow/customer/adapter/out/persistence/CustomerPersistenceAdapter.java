package com.breaditnow.customer.adapter.out.persistence;

import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.adapter.out.persistence.entity.CustomerEntity;
import com.breaditnow.customer.adapter.out.persistence.repository.JpaCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.breaditnow.common.exception.CustomerErrorCode.CUSTOMER_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = new CustomerEntity(customer);
        entity = jpaCustomerRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public List<Customer> findAllByIdIn(List<Long> customerIds) {
        return jpaCustomerRepository.findAllByIdIn(customerIds).stream()
                .map(CustomerEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(CustomerEntity::toDomain);
    }

    @Override
    public Customer getCustomer(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND));
    }


    @Override
    public Boolean isExistNickName(String nickname) {
        return jpaCustomerRepository.existsByNickname(nickname);
    }
}
