package com.breaditnow.region.application;

import com.breaditnow.common.application.request.GeoPointRequest;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.adapter.out.persistence.repository.AddressPort;
import com.breaditnow.customer.application.dto.AddressInfo;
import com.breaditnow.region.application.dto.response.LocationRegionResponse;
import com.breaditnow.region.domain.model.Region;
import com.breaditnow.region.domain.port.out.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.breaditnow.common.exception.CustomerErrorCode.REGION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {
    private final AddressPort addressPort;
    private final RegionRepository regionRepository;

    public LocationRegionResponse getGugunByCoordinates(GeoPointRequest request) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.longitude(), request.latitude())
                .orElseThrow(() -> new CustomerException(REGION_NOT_FOUND));

        Region region = regionRepository.getRegionByName(addressInfo);
        return LocationRegionResponse.of(region);
    }
}
