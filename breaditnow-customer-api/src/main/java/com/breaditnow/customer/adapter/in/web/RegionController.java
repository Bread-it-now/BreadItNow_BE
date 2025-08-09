package com.breaditnow.customer.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.application.request.GeoPointRequest;
import com.breaditnow.customer.application.RegionService;
import com.breaditnow.customer.adapter.out.persistence.RegionAdapter;
import com.breaditnow.customer.application.dto.response.GugunResponse;
import com.breaditnow.customer.application.dto.response.LocationRegionResponse;
import com.breaditnow.customer.application.dto.response.SidoResponse;
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
    private final RegionService regionService;

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
        return ApiSuccessResponse.of(regionService.getGugunByCoordinates(geoPointRequest));
    }
}
