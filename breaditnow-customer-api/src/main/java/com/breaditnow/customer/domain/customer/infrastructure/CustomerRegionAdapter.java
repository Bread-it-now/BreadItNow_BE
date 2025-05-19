package com.breaditnow.customer.domain.customer.infrastructure;

import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.customer.core.port.CustomerRegionPort;
import com.breaditnow.customer.domain.customer.infrastructure.entity.CustomerRegionEntity;
import com.breaditnow.customer.domain.customer.infrastructure.jpa.JpaCustomerRegionRepository;
import com.breaditnow.customer.domain.region.core.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRegionAdapter implements CustomerRegionPort {
    private final JpaCustomerRegionRepository jpaCustomerRegionRepository;

    @Override
    public void preference(Customer customer, Region region) {
        CustomerRegionEntity customerRegionEntity = new CustomerRegionEntity(customer, region);
        jpaCustomerRegionRepository.save(customerRegionEntity);
    }

    @Override
    public boolean checkPreference(Customer customer, Region region) {
        CustomerRegionEntity customerRegionEntity = new CustomerRegionEntity(customer, region);
        return jpaCustomerRegionRepository.existsById(customerRegionEntity.getId());
    }

    @Override
    public void preferenceAll(Customer customer, List<Region> regions) {
        jpaCustomerRegionRepository.deleteAllById_CustomerId(customer.getId());
        for (Region region : regions) {
            preference(customer, region);
        }
    }
}
