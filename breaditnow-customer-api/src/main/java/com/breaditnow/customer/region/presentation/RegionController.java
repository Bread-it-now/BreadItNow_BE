package com.breaditnow.customer.region.presentation;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.region.application.RegionQueryService;
import com.breaditnow.customer.region.infrastructure.RegionAdapter;
import com.breaditnow.customer.region.presentation.res.GugunResponse;
import com.breaditnow.customer.region.presentation.res.LocationRegionResponse;
import com.breaditnow.customer.region.presentation.res.SidoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
@Slf4j
public class RegionController {
    private final RegionAdapter regionAdapter;
    private final RegionQueryService regionQueryService;

    @GetMapping("/sido")
    public ApiSuccessResponse<List<SidoResponse>> getSidoList() {
        return ApiSuccessResponse.of(regionAdapter.findSidoResponses());
    }

    @GetMapping("/sido/{sidoCode}/gugun")
    public ApiSuccessResponse<List<GugunResponse>> getGugunListBySido(@PathVariable("sidoCode") String sidoCode) {
        return ApiSuccessResponse.of(regionAdapter.findGugunResponsesBySidoCode(sidoCode));
    }

    @GetMapping("/location")
    public ApiSuccessResponse<LocationRegionResponse> getGugunByCoordinates(GeoPointRequest geoPointRequest) {
        return ApiSuccessResponse.of(regionQueryService.getGugunByCoordinates(geoPointRequest));
    }
}
