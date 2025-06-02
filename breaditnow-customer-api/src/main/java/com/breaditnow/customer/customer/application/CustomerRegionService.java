package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.RegionUpdateRequest;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.domain.port.LoadCustomerPort;
import com.breaditnow.customer.customer.domain.port.SaveCustomerRegionPort;
import com.breaditnow.customer.region.application.port.LoadRegionPort;
import com.breaditnow.customer.region.core.Region;
import com.breaditnow.customer.region.core.RegionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerRegionService {
    private final LoadCustomerPort loadCustomerPort;
    private final SaveCustomerRegionPort saveCustomerRegionPort;
    private final LoadRegionPort loadRegionPort;

    @Transactional
    public void updateRegion(Long customerId, RegionUpdateRequest dto) {
        Customer customer = loadCustomerPort.findById(customerId);

        List<RegionId> regionIds = dto.gugunCodes().stream()
                .map(gugun -> new RegionId(dto.sidoCode(), gugun, null))
                .toList();

        List<Region> regions = new ArrayList<>();
        for (RegionId regionId : regionIds) {
            regions.add(loadRegionPort.findById(regionId));
        }

        saveCustomerRegionPort.preferenceAll(customer, regions);
    }
}
