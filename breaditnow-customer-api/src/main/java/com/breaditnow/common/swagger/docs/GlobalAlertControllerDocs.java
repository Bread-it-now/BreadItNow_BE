package com.breaditnow.common.swagger.docs;

import com.breaditnow.alert.application.request.GlobalAlertUpdateRequest;
import com.breaditnow.alert.application.response.GlobalAlertResponse;
import com.breaditnow.alert.application.response.GlobalAlertToggleResponse;
import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GlobalAlertControllerDocs {

    @Operation(summary = "방해 금지 설정 조회", description = "고객의 방해 금지 설정을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/do-not-disturb")
    ApiSuccessResponse<GlobalAlertResponse> getDoNotDisturbSetting(@AuthCustomer Long customerId);

    @Operation(summary = "방해 금지 설정 변경", description = "고객의 방해 금지 설정을 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/do-not-disturb")
    ApiSuccessResponse<Void> updateDoNotDisturbSetting(@AuthCustomer Long customerId, @RequestBody GlobalAlertUpdateRequest request);

    @Operation(summary = "방해 금지 설정 토글", description = "고객의 방해 금지 설정을 토글합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토글 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/do-not-disturb/toggle")
    ApiSuccessResponse<GlobalAlertToggleResponse> toggleDoNotDisturb(@AuthCustomer Long customerId);
}
