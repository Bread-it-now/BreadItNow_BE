package com.breaditnow.customer.domain.region.controller;

import java.util.List;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.region.controller.res.GugunResponse;
import com.breaditnow.customer.domain.region.controller.res.SidoResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Customer - 지역 API", description = "지역 정보(시도, 구군) 조회 API입니다.\"")
public interface RegionControllerDocs {

	@Operation(summary = "전체 시도 목록 조회", description = "전체 시도의 목록을 조회하여 반환합니다.")
	ApiSuccessResponse<List<SidoResponse>> getSidoList();

	@Operation(summary = "구군 목록 조회", description = "주어진 시도 코드를 기반으로 해당 시도의 구군 목록을 조회하여 반환합니다.")
	@Parameter(name = "sidoCode", description = "조회할 시도 코드", example = "11", required = true)
	ApiSuccessResponse<List<GugunResponse>> getGugunListBySido(String sidoCode);
}
