package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.RegionUpdateRequest;
import com.breaditnow.customer.customer.application.port.out.SaveCustomerRegionPort;
import com.breaditnow.customer.region.domain.port.LoadRegionPort;
import com.breaditnow.domain.global.exception.DomainErrorCode;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerRegionService {
    private final LoadRegionPort loadRegionPort;
    private final SaveCustomerRegionPort saveCustomerRegionPort;

    @Transactional
    public void updateRegion(Long customerId, RegionUpdateRequest dto) {
        saveCustomerRegionPort.delete(customerId);
        for (String gugunCode : dto.gugunCodes()) {
            if(!loadRegionPort.existsBySidoAndGugunCode(dto.sidoCode() + gugunCode)) {
                throw new DomainException(DomainErrorCode.REGION_NOT_FOUND);
            }
            saveCustomerRegionPort.preference(customerId, dto.sidoCode(), gugunCode);
        }
    }
}
