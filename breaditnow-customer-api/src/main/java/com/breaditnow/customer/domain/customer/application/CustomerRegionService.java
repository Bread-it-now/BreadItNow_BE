package com.breaditnow.customer.domain.customer.application;

import com.breaditnow.customer.domain.customer.application.request.RegionUpdateRequest;
import com.breaditnow.customer.domain.customer.core.Customer;
import com.breaditnow.customer.domain.customer.core.port.CustomerPort;
import com.breaditnow.customer.domain.customer.core.port.CustomerRegionPort;
import com.breaditnow.customer.domain.region.application.port.RegionPort;
import com.breaditnow.customer.domain.region.core.Region;
import com.breaditnow.customer.domain.region.core.RegionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerRegionService {
    private final CustomerPort customerPort;
    private final CustomerRegionPort customerRegionPort;
    private final RegionPort regionPort;

    @Transactional
    public void updateRegion(Long customerId, RegionUpdateRequest dto) {
        Customer customer = customerPort.findById(customerId);

        List<RegionId> regionIds = dto.gugunCodes().stream()
                .map(gugun -> new RegionId(dto.sidoCode(), gugun, null))
                .toList();

        List<Region> regions = new ArrayList<>();
        for (RegionId regionId : regionIds) {
            regions.add(regionPort.findById(regionId));
        }

        customerRegionPort.preferenceAll(customer, regions);
    }
}
