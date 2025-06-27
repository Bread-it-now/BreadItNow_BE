package com.breaditnow.customer.reservation.adapter.out.http;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.reservation.application.dto.request.ReservationCreateRequest;
import com.breaditnow.customer.reservation.domain.out.ReservationApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ReservationApiAdapter implements ReservationApiPort {
    private final WebClient reservationServiceClient;

    @Override
    public Long createReservation(Long customerId, ReservationCreateRequest request) {
        var responseType = new ParameterizedTypeReference<ApiSuccessResponse<Long>>() {};

        return reservationServiceClient.post()
                .uri("/internal/api/v1/reservation")
                .header("X-Customer-Id", String.valueOf(customerId))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .map(ApiSuccessResponse::data)
                .block();
    }
}
