package com.breaditnow.customer.region.application;

import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.location.application.AddressInfo;
import com.breaditnow.customer.location.application.port.out.AddressPort;
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
    private final AddressPort addressPort;
    private final LoadRegionPort loadRegionPort;

    public LocationRegionResponse getGugunByCoordinates(GeoPointRequest request) {
        AddressInfo addressInfo = addressPort.getAddressInfo(request.longitude(), request.latitude())
                .orElseThrow(() -> new IllegalArgumentException("주소 정보를 찾을 수 없습니다."));

        Region region = loadRegionPort.getRegionByName(
                addressInfo.getSidoName(),
                addressInfo.getGugunName(),
                addressInfo.getDongName()
        );
        return LocationRegionResponse.of(region.getRegionCode(), region.getSidoName(), region.getGugunName());
    }
}
