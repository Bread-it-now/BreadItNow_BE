package com.breaditnow.customer.domain.region.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.region.controller.res.GugunResponse;
import com.breaditnow.customer.domain.region.controller.res.SidoResponse;
import com.breaditnow.customer.domain.region.service.RegionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
@Slf4j
public class RegionController implements RegionControllerDocs {
	private final RegionService regionService;

	@GetMapping("/sido")
	public ApiSuccessResponse<List<SidoResponse>> getSidoList() {
		return ApiSuccessResponse.of(regionService.getSidoList());
	}

	@GetMapping("/sido/{sidoCode}/gugun")
	public ApiSuccessResponse<List<GugunResponse>> getGugunListBySido(
		@PathVariable("sidoCode") String sidoCode) {
		return ApiSuccessResponse.of(regionService.getGugunListBySido(sidoCode));
	}

	/*@GetMapping("/gugun/{gugunCode}/dong")
	public ApiSuccessResponse<List<DongResponse>> getDongListByGugun(
		@PathVariable("gugunCode") String gugunCode) {
		return ApiSuccessResponse.of(regionService.getDongListByGugun(gugunCode));
	}*/
}
