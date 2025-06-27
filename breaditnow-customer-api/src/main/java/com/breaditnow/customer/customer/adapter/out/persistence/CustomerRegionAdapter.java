package com.breaditnow.customer.customer.adapter.out.persistence;

import com.breaditnow.customer.customer.domain.port.out.SaveCustomerRegionPort;
import com.breaditnow.customer.customer.adapter.out.persistence.entity.CustomerRegionEntity;
import com.breaditnow.customer.customer.adapter.out.persistence.repository.JpaCustomerRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRegionAdapter implements SaveCustomerRegionPort {
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
