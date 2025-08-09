package com.breaditnow.customer.application;

import com.breaditnow.common.exception.CustomerErrorCode;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.domain.port.out.CustomerRegionRepository;
import com.breaditnow.customer.application.dto.request.RegionUpdateRequest;
import com.breaditnow.customer.domain.port.out.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerRegionService {
    private final RegionRepository regionRepository;
    private final CustomerRegionRepository customerRegionRepository;

    @Transactional
    public void updateRegion(Long customerId, RegionUpdateRequest dto) {
        customerRegionRepository.delete(customerId);
        for (String gugunCode : dto.gugunCodes()) {
            if(!regionRepository.existsBySidoAndGugunCode(dto.sidoCode() + gugunCode)) {
                throw new CustomerException(CustomerErrorCode.REGION_NOT_FOUND);
            }
            customerRegionRepository.preference(customerId, dto.sidoCode(), gugunCode);
        }
    }
}
