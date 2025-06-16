package com.breaditnow.customer.region.application;

import com.breaditnow.common.client.kakao.GeoLocationClient;
import com.breaditnow.common.client.kakao.dto.AddressNameDto;
import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.region.domain.Region;
import com.breaditnow.customer.region.domain.port.LoadRegionPort;
import com.breaditnow.customer.region.presentation.res.LocationRegionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionQueryService {
    private final GeoLocationClient geoLocationClient;
    private final LoadRegionPort loadRegionPort;

    public LocationRegionResponse getGugunByCoordinates(GeoPointRequest geoPointRequest) {
        AddressNameDto addressNameDto = geoLocationClient.lookupAddress(geoPointRequest.latitude(), geoPointRequest.longitude());
        Region region = loadRegionPort.getRegionByName(addressNameDto.getSidoName(), addressNameDto.getGugunName(), addressNameDto.getDongName());
        return LocationRegionResponse.of(region.getRegionCode(), region.getSidoName(), region.getGugunName());
    }
}
