package com.breaditnow.customer.adapter.out.persistence;

import com.breaditnow.customer.domain.port.out.CustomerRegionRepository;
import com.breaditnow.customer.adapter.out.persistence.entity.CustomerRegionEntity;
import com.breaditnow.customer.adapter.out.persistence.repository.JpaCustomerRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRegionAdapter implements CustomerRegionRepository {
    private final JpaCustomerRegionRepository jpaCustomerRegionRepository;

    @Override
    public void preference(Long customerId, String sidoCode, String gugunCode) {
        CustomerRegionEntity customerRegionEntity = new CustomerRegionEntity(customerId, sidoCode, gugunCode);
        jpaCustomerRegionRepository.save(customerRegionEntity);
    }

    public void delete(Long customerId) {
        jpaCustomerRegionRepository.deleteByCustomerId(customerId);
    }
}
