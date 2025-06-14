package com.breaditnow.customer.region.service;

import com.breaditnow.common.client.kakao.GeoLocationClient;
import com.breaditnow.common.client.kakao.dto.AddressNameDto;
import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.region.application.port.LoadRegionPort;
import com.breaditnow.customer.region.core.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {
    private final GeoLocationClient geoLocationClient;
    private final LoadRegionPort loadRegionPort;

    public Region getGugunByCoordinates(GeoPointRequest geoPointRequest) {
        AddressNameDto addressNameDto = geoLocationClient.lookupAddress(geoPointRequest.latitude(), geoPointRequest.longitude());
        return loadRegionPort.getRegionByName(addressNameDto.getSidoName(), addressNameDto.getGugunName(), addressNameDto.getDongName());
    }
}
