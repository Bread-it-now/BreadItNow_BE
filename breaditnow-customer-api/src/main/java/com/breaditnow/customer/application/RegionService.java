package com.breaditnow.customer.application;

import com.breaditnow.common.application.request.GeoPointRequest;
import com.breaditnow.customer.application.dto.AddressInfo;
import com.breaditnow.customer.adapter.out.persistence.repository.AddressRepository;
import com.breaditnow.customer.domain.model.Region;
import com.breaditnow.customer.domain.port.out.RegionRepository;
import com.breaditnow.customer.application.dto.response.LocationRegionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {
    private final AddressRepository addressRepository;
    private final RegionRepository regionRepository;

    public LocationRegionResponse getGugunByCoordinates(GeoPointRequest request) {
        AddressInfo addressInfo = addressRepository.getAddressInfo(request.longitude(), request.latitude())
                .orElseThrow(() -> new IllegalArgumentException("주소 정보를 찾을 수 없습니다."));

        Region region = regionRepository.getRegionByName(
                addressInfo.getSidoName(),
                addressInfo.getGugunName(),
                addressInfo.getDongName()
        );
        return LocationRegionResponse.of(region.getRegionCode(), region.getSidoName(), region.getGugunName());
    }
}
